package com.example.nineg.data.db

import com.example.nineg.data.db.dto.UserDto
import com.example.nineg.data.db.remote.UserRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): CalendarRepository {
    override suspend fun createUser(deviceId: String) {
        userRemoteDataSource.createUser(deviceId, "", "", "")
    }

    override suspend fun searchUser(deviceId: String): Response<UserDto> {
        return userRemoteDataSource.searchUser(deviceId)
    }
}