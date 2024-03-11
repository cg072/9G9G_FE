package com.team.nineg.data.db

import android.content.Context
import android.content.SharedPreferences
import com.team.nineg.data.db.domain.User
import com.team.nineg.data.db.dto.asDomainModel
import com.team.nineg.data.db.remote.UserRemoteDataSource
import com.team.nineg.retrofit.ApiResult
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    private val statusPref: SharedPreferences =
        context.getSharedPreferences("STATUS_PREFS", Context.MODE_PRIVATE)

    override suspend fun createUser(deviceId: String): ApiResult<User> {
        val response = userRemoteDataSource.createUser(deviceId, "", "", "")
        return try {
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!.asDomainModel())
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    override suspend fun searchUser(deviceId: String): ApiResult<User> {
        val response = userRemoteDataSource.searchUser(deviceId)
        return try {
            if (response.isSuccessful && response.body() != null) {
                statusPref.edit().putString(USER_ID, deviceId).apply()
                ApiResult.Success(response.body()!!.asDomainModel())
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    override suspend fun loginUser(deviceId: String): ApiResult<User> {
        val response = userRemoteDataSource.searchUser(deviceId)

        return try {
            if (response.isSuccessful && response.body() != null) {
                statusPref.edit().putString(USER_ID, deviceId).apply()
                ApiResult.Success(response.body()!!.asDomainModel())
            } else {
                createUser(deviceId)
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