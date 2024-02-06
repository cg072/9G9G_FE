package com.example.nineg.data.db.local

import com.example.nineg.data.MissionCardInfo

interface MissionCardLocalDataSource {

    // 카드 리스트 반환
    suspend fun getMissionCardList(): List<MissionCardInfo>

    // 카드 추가.
    suspend fun addMissionCard(missionCardInfo: MissionCardInfo)

    // 카드 리스트 추가
    suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfo>)

    // 카드 리스트 클리어
    suspend fun clearMissionCard()

    // 등록된 오늘의 구디 카드 반환
    suspend fun getTodayMissionCard(): MissionCardInfo?

    // 즐겨찾기된 카드 리스트 반환
    suspend fun getBookmarkedMissionCardList(): List<MissionCardInfo>

    // 즐겨찾기 등록
    suspend fun bookmarkMissionCard(position: Int)

    // 즐겨찾기 해제
    suspend fun unBookmarkMissionCard(position: Int)
}