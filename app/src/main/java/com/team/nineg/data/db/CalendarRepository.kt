package com.team.nineg.data.db

import com.team.nineg.data.db.dto.UserDto
import retrofit2.Response

interface CalendarRepository {

    suspend fun createUser(deviceId: String)

    suspend fun searchUser(deviceId: String): Response<UserDto>
}