package com.team.nineg.data.db

import com.team.nineg.data.db.domain.User
import com.team.nineg.data.db.dto.RevokeDto
import com.team.nineg.retrofit.ApiResult

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun login(accessToken: String): ApiResult<User>
    suspend fun logout(): ApiResult<Unit>
    suspend fun revoke(deviceId: String): ApiResult<RevokeDto>
}