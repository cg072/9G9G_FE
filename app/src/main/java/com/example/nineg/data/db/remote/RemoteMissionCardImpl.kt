package com.example.nineg.data.db.remote

import com.example.nineg.data.MissionCardInfo
import javax.inject.Inject

class RemoteMissionCardImpl @Inject constructor(): RemoteMissionCardRepository {
    override suspend fun getMissionCardList(): MutableList<MissionCardInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfo>) {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayMissionCard(): MissionCardInfo? {
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarkedMissionCardList(): MutableList<MissionCardInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun bookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unBookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

}