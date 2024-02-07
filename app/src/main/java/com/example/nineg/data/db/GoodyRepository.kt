package com.example.nineg.data.db

import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.retrofit.ApiResult
import okhttp3.MultipartBody
import retrofit2.Response

interface GoodyRepository {
    suspend fun registerGoody(
        deviceId: String,
        missionTitle: String,
        title: String,
        content: String,
        photoUrl: String,
        image: MultipartBody.Part
    ): ApiResult<GoodyDto>

    suspend fun removeGoody(goodyId: String): Response<Unit>

    suspend fun getGoody(deviceId: String): Response<GoodyDto>

    suspend fun getGoodyList(deviceId: String): Response<GoodyDto>
}