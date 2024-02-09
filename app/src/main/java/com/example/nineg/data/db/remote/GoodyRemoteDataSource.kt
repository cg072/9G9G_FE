package com.example.nineg.data.db.remote

import com.example.nineg.data.db.dto.GoodyDto
import okhttp3.MultipartBody
import retrofit2.Response

interface GoodyRemoteDataSource {
    suspend fun registerGoody(
        deviceId: String,
        missionTitle: String,
        title: String,
        content: String,
        photoUrl: String,
        image: MultipartBody.Part
    ): Response<GoodyDto>

    suspend fun removeGoody()

    suspend fun updateGoody()

    suspend fun getGoody()

    suspend fun getGoodyList()
}