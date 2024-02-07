package com.example.nineg.data.db

import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.data.db.remote.GoodyRemoteDataSource
import com.example.nineg.retrofit.ApiResult
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class GoodyRepositoryImpl @Inject constructor(private val goodyRemoteDataSource: GoodyRemoteDataSource) :
    GoodyRepository {
    override suspend fun registerGoody(
        deviceId: String,
        missionTitle: String,
        title: String,
        content: String,
        photoUrl: String,
        image: MultipartBody.Part
    ): ApiResult<GoodyDto> {
        return try {
            val response = goodyRemoteDataSource.registerGoody(
                deviceId,
                missionTitle,
                title,
                content,
                photoUrl,
                image
            )

            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), null)
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    override suspend fun removeGoody(goodyId: String): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getGoody(deviceId: String): Response<GoodyDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getGoodyList(deviceId: String): Response<GoodyDto> {
        TODO("Not yet implemented")
    }
}