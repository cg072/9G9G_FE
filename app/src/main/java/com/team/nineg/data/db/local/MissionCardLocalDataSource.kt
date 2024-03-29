package com.team.nineg.data.db.local

import com.team.nineg.data.db.entity.MissionCardInfoEntity

interface MissionCardLocalDataSource {

    // 카드 리스트 반환
    suspend fun getMissionCardList(): List<MissionCardInfoEntity>

    suspend fun getMissionCardPack(): List<MissionCardInfoEntity>

    // 카드 추가.
    suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity)

    // 카드 리스트 추가
    suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>)

    // 카드 리스트 클리어
    suspend fun clearMissionCard()

    // 등록된 오늘의 구디 카드 반환
    suspend fun getTodayMissionCard(): MissionCardInfoEntity?

    // 즐겨찾기된 카드 리스트 반환
    suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity>

    // 즐겨찾기 등록
    suspend fun bookmarkMissionCard(id: Int)

    // 즐겨찾기 해제
    suspend fun unBookmarkMissionCard(id: Int)
}