package com.team.nineg.data.db.local

import com.team.nineg.data.db.room.MissionCardDao
import com.team.nineg.data.db.entity.MissionCardInfoEntity
import javax.inject.Inject

class MissionCardLocalDataSourceImpl @Inject constructor(
    private val missionCardDao: MissionCardDao
): MissionCardLocalDataSource {
    override suspend fun getMissionCardList(): List<MissionCardInfoEntity> {
        return missionCardDao.getMissionCardList()
    }

    override suspend fun getMissionCardPack(): List<MissionCardInfoEntity> {
        val missionCardList = missionCardDao.getMissionCardList().shuffled()

        val levelCardCount = mapOf(1 to 2, 2 to 2, 3 to 1)

        return missionCardList.groupBy { it.level }
            .flatMap { (level, cards) ->
                cards.take(levelCardCount[level] ?: 0)
            }
    }

    override suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        missionCardDao.insertMissionCard(missionCardInfo)
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
        return missionCardDao.getBookmarkedMissionCardList()
    }

    override suspend fun bookmarkMissionCard(id: Int) {
        missionCardDao.bookmarkMissionCard(id, true)
    }

    override suspend fun unBookmarkMissionCard(id: Int) {
        missionCardDao.bookmarkMissionCard(id, false)
    }
}