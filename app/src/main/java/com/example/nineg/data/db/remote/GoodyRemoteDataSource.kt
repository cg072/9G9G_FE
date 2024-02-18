package com.example.nineg.data.db.remote

import com.example.nineg.data.db.dto.GoodyDto
import okhttp3.MultipartBody
import retrofit2.Response

interface GoodyRemoteDataSource {
    suspend fun registerGoody(
        deviceId: String,
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ): Response<GoodyDto>

    suspend fun removeGoody(goodyId: String): Response<Unit>

    suspend fun updateGoody(
        goodyId: String,
        title: String?,
        content: String?,
        dueDate: String?,
        image: MultipartBody.Part?
    ): Response<GoodyDto>

    suspend fun getGoodyList(deviceId: String): Response<List<GoodyDto>>
}