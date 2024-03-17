package com.team.nineg.data.db.remote

import com.team.nineg.data.db.dto.UserDto
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun login(accessToken: String): Response<UserDto>
    suspend fun revoke(deviceId: String): Response<UserDto>
}