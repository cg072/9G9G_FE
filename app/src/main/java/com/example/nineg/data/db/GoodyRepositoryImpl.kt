package com.example.nineg.data.db

import com.example.nineg.data.db.domain.Goody
import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.data.db.dto.asDomainModel
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
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ): ApiResult<Goody> {
        return try {
            val response = goodyRemoteDataSource.registerGoody(
                deviceId,
                title,
                content,
                dueDate,
                image
            )

            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!.asDomainModel())
            } else {
                ApiResult.Error(response.code())
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    override suspend fun removeGoody(goodyId: String): ApiResult<Unit> {
        return try {
            val response = goodyRemoteDataSource.removeGoody(goodyId)

            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error(response.code())
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }

    override suspend fun getGoodyList(deviceId: String): ApiResult<List<Goody>> {
        return try {
            val response = goodyRemoteDataSource.getGoodyList(deviceId)

            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()?.map { it.asDomainModel() } ?: emptyList())
            } else {
                ApiResult.Error(response.code())
            }
        } catch (throwable: Throwable) {
            val code = (throwable as? HttpException)?.code()
            ApiResult.Error(code, throwable)
        }
    }
}