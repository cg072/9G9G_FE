package com.team.nineg.data.db

import com.team.nineg.data.db.domain.User
import com.team.nineg.retrofit.ApiResult

interface UserRepository {
    suspend fun createUser(deviceId: String): ApiResult<User>
    suspend fun searchUser(deviceId: String): ApiResult<User>
}