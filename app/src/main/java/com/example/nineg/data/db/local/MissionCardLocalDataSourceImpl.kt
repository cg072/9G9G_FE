package com.example.nineg.data.db.local

import com.example.nineg.data.db.room.MissionCardDao
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import javax.inject.Inject

class MissionCardLocalDataSourceImpl @Inject constructor(
    private val missionCardDao: MissionCardDao
): MissionCardLocalDataSource {
    override suspend fun getMissionCardList(): List<MissionCardInfoEntity> {
        return missionCardDao.getMissionCardList()
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        missionCardDao.insertMissionCard(missionCardInfoList)
    }

    override suspend fun clearMissionCard() {
        missionCardDao.clearMissionCard()
    }

    override suspend fun getTodayMissionCard(): MissionCardInfoEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun bookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unBookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }
}