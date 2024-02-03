package com.example.nineg.data.db

import com.example.nineg.data.MissionCardInfo
import com.example.nineg.data.db.local.LocalMissionCardRepository
import com.example.nineg.data.db.remote.RemoteMissionCardRepository
import javax.inject.Inject

class MissionCardRepositoryImpl @Inject constructor(
    private val localMissionCardImpl: LocalMissionCardRepository,
    private val remoteMissionCardImpl: RemoteMissionCardRepository
) : MissionCardRepository {
    override suspend fun getMissionCardList(): List<MissionCardInfo> {
        //        TODO("Not yet implemented")
        return localMissionCardImpl.getMissionCardList()
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfo) {
//        TODO("Not yet implemented")
        localMissionCardImpl.addMissionCard(missionCardInfo)
    }

    override suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfo>) {
//        TODO("Not yet implemented")
        localMissionCardImpl.addMissionCardList(missionCardInfoList)
    }

    override suspend fun clearMissionCard() {
        localMissionCardImpl.clearMissionCard()
    }

    override suspend fun getTodayMissionCard(): MissionCardInfo? {
//        TODO("Not yet implemented")
        return null
    }

    override suspend fun getBookmarkedMissionCardList(): List<MissionCardInfo> {
//        TODO("Not yet implemented")
        return listOf()
    }

    override suspend fun bookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unBookmarkMissionCard(position: Int) {
        TODO("Not yet implemented")
    }
}