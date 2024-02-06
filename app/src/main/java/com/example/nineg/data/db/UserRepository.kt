package com.example.nineg.data.db

import com.example.nineg.data.db.domain.User
import com.example.nineg.retrofit.ApiResult

interface UserRepository {
    suspend fun createUser(deviceId: String): ApiResult<User>
    suspend fun searchUser(deviceId: String): ApiResult<User>
}