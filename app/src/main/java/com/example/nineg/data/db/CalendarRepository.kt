package com.example.nineg.data.db

import com.example.nineg.data.db.dto.UserDto
import retrofit2.Response

interface CalendarRepository {

    suspend fun createUser(deviceId: String)

    suspend fun searchUser(deviceId: String): Response<UserDto>
}