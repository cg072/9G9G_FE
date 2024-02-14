package com.example.nineg.data.db.local

import android.util.Log
import com.example.nineg.data.db.entity.MissionCardInfoEntity

data class MissionCards(private val missionCardList: MutableList<MissionCardInfoEntity> = mutableListOf()) {

    private var count = 0

    // 카드 리스트 반환
    fun getMissionCardList(): List<MissionCardInfoEntity> {
        return missionCardList.toList()
    }

    // 카드 추가.
    private fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        missionCardInfo.id = count++
        missionCardList.add(missionCardInfo)
    }

    // 카드 리스트 추가
    fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        for (missionCardInfo in missionCardInfoList) {
            missionCardInfo.id = count++
            addMissionCard(missionCardInfo)
        }
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