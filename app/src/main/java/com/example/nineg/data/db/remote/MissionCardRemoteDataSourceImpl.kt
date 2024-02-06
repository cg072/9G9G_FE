package com.example.nineg.data.db.remote

import com.example.nineg.data.db.entity.MissionCardInfoEntity
import javax.inject.Inject

class MissionCardRemoteDataSourceImpl @Inject constructor(): MissionCardRemoteDataSource {
    override suspend fun getMissionCardList(): MutableList<MissionCardInfoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayMissionCard(): MissionCardInfoEntity? {
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

}