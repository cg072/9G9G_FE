package com.example.nineg.data.db

import com.example.nineg.data.db.domain.Goody
import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.retrofit.ApiResult
import okhttp3.MultipartBody
import retrofit2.Response

interface GoodyRepository {
    suspend fun registerGoody(
        deviceId: String,
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ): ApiResult<Goody>

    suspend fun removeGoody(goodyId: String): ApiResult<Unit>

    suspend fun updateGoody(
        goodyId: String,
        title: String?,
        content: String?,
        dueDate: String?,
        image: MultipartBody.Part?
    ): ApiResult<Goody>

    suspend fun getGoodyList(deviceId: String): ApiResult<List<Goody>>
}