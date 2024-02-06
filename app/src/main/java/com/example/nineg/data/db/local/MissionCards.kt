package com.example.nineg.data.db.local

import com.example.nineg.data.db.entity.MissionCardInfoEntity

data class MissionCards(private val missionCardList: MutableList<MissionCardInfoEntity> = mutableListOf()) {

    // 카드 리스트 반환
    fun getMissionCardList(): List<MissionCardInfoEntity> {
        return missionCardList
    }

    // 카드 추가.
    fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        missionCardList.add(missionCardInfo)
    }

    // 카드 리스트 추가
    fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        missionCardList.addAll(missionCardInfoList)
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