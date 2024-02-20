package com.team.nineg.data.db.remote

import com.team.nineg.data.db.dto.UserDto
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun createUser(
        deviceId: String,
        name: String,
        age: String,
        gender: String
    ): Response<UserDto>

    suspend fun searchUser(deviceId: String): Response<UserDto>
    suspend fun createUser(deviceId: String): Response<UserDto>
}