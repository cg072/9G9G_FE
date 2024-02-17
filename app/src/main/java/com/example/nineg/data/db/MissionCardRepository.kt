package com.example.nineg.data.db

import com.example.nineg.data.db.dto.GoodyDto
import com.example.nineg.data.db.entity.MissionCardInfoEntity

interface MissionCardRepository {

    // 카드 리스트 반환
    suspend fun getMissionCardList(): List<MissionCardInfoEntity>

    // 카드 리스트 반환(레벨 혼합 총 5개)
    suspend fun getMissionCardPack(): List<MissionCardInfoEntity>

    // 카드 추가.
    suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity)

    // 카드 리스트 추가
    suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>)

    // 카드 리스트 클리어
    suspend fun clearMissionCard()

    // 등록된 오늘의 구디 카드 반환
    suspend fun getTodayMissionCard(userId: String): GoodyDto?

    // 즐겨찾기된 카드 리스트 반환
    suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity>

    // 즐겨찾기 등록
    suspend fun bookmarkMissionCard(id: Int)

    // 즐겨찾기 해제
    suspend fun unBookmarkMissionCard(id: Int)

    fun getIsFirstLaunch(): Boolean

    fun setIsFirstLaunch(isFirstLaunch: Boolean)

    suspend fun downloadMissionCardList()

    fun getUserId(): String
    fun setUserId(userId: String)
}