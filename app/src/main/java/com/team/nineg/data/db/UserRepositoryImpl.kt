package com.team.nineg.data.db

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.team.nineg.data.db.domain.User
import com.team.nineg.data.db.dto.RevokeDto
import com.team.nineg.data.db.dto.asDomainModel
import com.team.nineg.data.db.dto.asEntityModel
import com.team.nineg.data.db.entity.UserEntity
import com.team.nineg.data.db.entity.asDomainModel
import com.team.nineg.data.db.local.UserLocalDataSource
import com.team.nineg.data.db.remote.UserRemoteDataSource
import com.team.nineg.retrofit.ApiResult
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
): UserRepository {
    override suspend fun getUser(): User? {
        return userLocalDataSource.getUser()?.asDomainModel()
    }

    override suspend fun login(accessToken: String): ApiResult<User> {
        val response = userRemoteDataSource.login(accessToken)

        return try {
            if (response.isSuccessful && response.body() != null) {
                userLocalDataSource.insert(response.body()!!.asEntityModel())
                ApiResult.Success(response.body()!!.asDomainModel())
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    override suspend fun logout(): ApiResult<Unit> {
        userLocalDataSource.clear()
        return ApiResult.Success(Unit)
    }

    override suspend fun revoke(deviceId: String): ApiResult<RevokeDto> {
        val response = userRemoteDataSource.revoke(deviceId)

        return try {
            if (response.isSuccessful && response.body() != null) {
                userLocalDataSource.clear()
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }
}