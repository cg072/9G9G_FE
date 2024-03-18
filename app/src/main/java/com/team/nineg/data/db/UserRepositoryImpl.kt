package com.team.nineg.data.db

import android.content.Context
import android.content.SharedPreferences
import com.team.nineg.data.db.domain.User
import com.team.nineg.data.db.dto.RevokeDto
import com.team.nineg.data.db.dto.asDomainModel
import com.team.nineg.data.db.remote.UserRemoteDataSource
import com.team.nineg.retrofit.ApiResult
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    private val statusPref: SharedPreferences =
        context.getSharedPreferences("STATUS_PREFS", Context.MODE_PRIVATE)

    override suspend fun login(accessToken: String): ApiResult<User> {
        val response = userRemoteDataSource.login(accessToken)

        return try {
            if (response.isSuccessful && response.body() != null) {
                statusPref.edit().putString(USER_ID, response.body()?.deviceId).apply()
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
        statusPref.edit().remove(USER_ID).apply()
        return ApiResult.Success(Unit)
    }

    override suspend fun revoke(deviceId: String): ApiResult<RevokeDto> {
        val response = userRemoteDataSource.revoke(deviceId)

        return try {
            if (response.isSuccessful && response.body() != null) {
                statusPref.edit().remove(USER_ID).apply()
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    companion object {
        private const val USER_ID = "userId"
    }
}