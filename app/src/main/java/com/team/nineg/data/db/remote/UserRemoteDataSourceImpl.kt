package com.team.nineg.data.db.remote

import com.team.nineg.data.db.dto.RevokeDto
import com.team.nineg.data.db.dto.UserDto
import com.team.nineg.retrofit.RetrofitService
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val service: RetrofitService) :
    UserRemoteDataSource {
    override suspend fun login(accessToken: String): Response<UserDto> {
        return service.login("Bearer $accessToken")
    }

    override suspend fun revoke(deviceId: String): Response<RevokeDto> {
        return service.revoke(deviceId)
    }
}