package com.team.nineg.data.db.remote

import android.util.Log
import com.team.nineg.data.db.dto.UserDto
import com.team.nineg.retrofit.RetrofitService
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

    override suspend fun createUser(
        deviceId: String,
    ): Response<UserDto> {
        val param = HashMap<String, String>()
        param["deviceId"] = deviceId
        Log.d("TAG", "createUser: ")
        return service.createUser(param)
    }

    override suspend fun searchUser(deviceId: String): Response<UserDto> {
        return service.searchUser(deviceId)
    }
}