package com.example.nineg.data.db.remote

import com.example.nineg.data.db.dto.MissionCardDto
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.retrofit.ApiResult
import com.example.nineg.retrofit.RetrofitService
import retrofit2.HttpException
import javax.inject.Inject

class MissionCardRemoteDataSourceImpl @Inject constructor(private val service: RetrofitService) :
    MissionCardRemoteDataSource {
    override suspend fun getMissionCardList(): MutableList<MissionCardInfoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarkedMissionCardList(): MutableList<MissionCardInfoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun bookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unBookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun downloadMissionCardList(): ApiResult<MissionCardDto> {
        return try {
            val response = service.getMissionCardList()
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
}