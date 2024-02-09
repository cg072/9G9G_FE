package com.example.nineg.data.db

import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.retrofit.ApiResult
import okhttp3.MultipartBody

interface GoodyRepository {
    suspend fun registerGoody(
        deviceId: String,
        missionTitle: String,
        title: String,
        content: String,
        photoUrl: String,
        image: MultipartBody.Part
    ): ApiResult<GoodyDto>

    suspend fun removeGoody()

    suspend fun updateGoody()

    suspend fun getGoody()

    suspend fun getGoodyList()
}