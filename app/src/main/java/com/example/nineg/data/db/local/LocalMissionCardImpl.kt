package com.example.nineg.data.db.local

import com.example.nineg.data.db.room.MissionCardDao
import com.example.nineg.data.MissionCardInfo
import javax.inject.Inject

class LocalMissionCardImpl @Inject constructor(
    private val missionCardDao: MissionCardDao
): LocalMissionCardRepository {
    override suspend fun getMissionCardList(): List<MissionCardInfo> {
        return missionCardDao.getMissionCardList()
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfo>) {
        missionCardDao.insertMissionCard(missionCardInfoList)
    }

    override suspend fun getTodayMissionCard(): MissionCardInfo? {
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarkedMissionCardList(): List<MissionCardInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun bookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unBookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }
}