package com.example.nineg.data.db

import com.example.nineg.data.db.domain.User
import com.example.nineg.data.db.dto.asDomainModel
import com.example.nineg.data.db.remote.UserRemoteDataSource
import com.example.nineg.retrofit.ApiResult
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun createUser(deviceId: String): ApiResult<User> {
        val response =  userRemoteDataSource.createUser(deviceId, "", "", "")
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
                ApiResult.Success(response.body()!!.asDomainModel())
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }
}