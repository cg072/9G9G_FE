package com.team.nineg.data.db

import com.team.nineg.data.db.domain.Goody
import com.team.nineg.data.db.dto.asDomainModel
import com.team.nineg.data.db.local.UserLocalDataSource
import com.team.nineg.data.db.remote.GoodyRemoteDataSource
import com.team.nineg.retrofit.ApiResult
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class GoodyRepositoryImpl @Inject constructor(
    private val goodyRemoteDataSource: GoodyRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : GoodyRepository {

    override suspend fun registerGoody(
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ): ApiResult<Goody> {
        return try {
            val userId = userLocalDataSource.getUser()?.deviceId ?: ""
            val response = goodyRemoteDataSource.registerGoody(
                userId,
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

    override suspend fun updateGoody(
        goodyId: String,
        title: String?,
        content: String?,
        dueDate: String?,
        image: MultipartBody.Part?
    ): ApiResult<Goody> {
        return try {
            val response = goodyRemoteDataSource.updateGoody(
                goodyId,
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

    override suspend fun getGoodyList(): ApiResult<List<Goody>> {
        return try {
            val userId = userLocalDataSource.getUser()?.deviceId ?: ""
            val response = goodyRemoteDataSource.getGoodyList(userId)

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