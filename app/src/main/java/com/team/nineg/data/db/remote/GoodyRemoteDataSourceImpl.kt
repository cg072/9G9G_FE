package com.team.nineg.data.db.remote

import com.team.nineg.data.db.dto.GoodyDto
import com.team.nineg.retrofit.RetrofitService
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class GoodyRemoteDataSourceImpl @Inject constructor(private val service: RetrofitService) :
    GoodyRemoteDataSource {
    override suspend fun registerGoody(
        deviceId: String,
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ): Response<GoodyDto> {
        val json = JsonObject()
        json.addProperty("deviceId", deviceId)
        json.addProperty("title", title)
        json.addProperty("content", content)
        json.addProperty("dueDate", dueDate)

        return service.registerGoody(
            image,
            json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        )
    }

    override suspend fun removeGoody(goodyId: String): Response<Unit> {
        return service.removeGoody(goodyId)
    }

    override suspend fun updateGoody(
        goodyId: String,
        title: String?,
        content: String?,
        dueDate: String?,
        image: MultipartBody.Part?
    ): Response<GoodyDto> {
        val json = JsonObject()
        json.addProperty("title", title)
        json.addProperty("content", content)
        json.addProperty("dueDate", dueDate)

        return service.updateGoody(
            goodyId,
            image,
            json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        )
    }

    override suspend fun getGoodyList(deviceId: String): Response<List<GoodyDto>> {
        return service.fetchGoodyList(deviceId)
    }
}