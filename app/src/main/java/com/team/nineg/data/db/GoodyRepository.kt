package com.team.nineg.data.db

import com.team.nineg.data.db.domain.Goody
import com.team.nineg.retrofit.ApiResult
import okhttp3.MultipartBody

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