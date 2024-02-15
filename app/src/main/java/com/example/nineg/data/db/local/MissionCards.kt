package com.example.nineg.data.db.local

import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.data.db.entity.MissionCardInfoEntity

data class MissionCards(private val missionCardList: MutableList<MissionCard> = mutableListOf()) {

    private var count = 0

    // 카드 리스트 반환
    fun getMissionCardList(): List<MissionCard> {
        return missionCardList.toList()
    }

    // 카드 추가.
    private fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        MissionCard(
            id = missionCardInfo.id,
            index = count++,
            image = missionCardInfo.image,
            level = missionCardInfo.level,
            title = missionCardInfo.title,
            subTitle = missionCardInfo.guide,
            isBookmarked = missionCardInfo.isBookmarked
        ).apply { missionCardList.add(this) }
    }

    // 카드 리스트 추가
    fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        val missionCardList = missionCardInfoList.map {
            MissionCard(
                id = it.id,
                index = count++,
                image = it.image,
                level = it.level,
                title = it.title,
                subTitle = it.guide,
            )
        }
        this.missionCardList.addAll(missionCardList)
    }

    // 즐겨찾기 등록
    fun bookmarkMissionCard(position: Int) {
        missionCardList[position].isBookmarked = true
    }

    // 즐겨찾기 해제
    fun unBookmarkMissionCard(position: Int) {
        missionCardList[position].isBookmarked = false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}