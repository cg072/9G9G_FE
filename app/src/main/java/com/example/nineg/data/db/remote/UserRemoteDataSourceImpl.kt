package com.example.nineg.data.db.remote

import com.example.nineg.data.db.dto.UserDto
import com.example.nineg.retrofit.RetrofitService
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val service: RetrofitService) :
    UserRemoteDataSource {

    override suspend fun createUser(
        deviceId: String,
        name: String,
        age: String,
        gender: String
    ): Response<UserDto> {
        val param = HashMap<String, String>()
        param["deviceId"] = deviceId
        param["name"] = name
        param["age"] = age
        param["gender"] = gender

        return service.createUser(param)
    }

    override suspend fun searchUser(deviceId: String): Response<UserDto> {
        return service.searchUser(deviceId)
    }
}