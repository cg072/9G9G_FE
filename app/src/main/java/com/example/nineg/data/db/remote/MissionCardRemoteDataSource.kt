package com.example.nineg.data.db.remote

import com.example.nineg.data.db.dto.MissionCardDto
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.retrofit.ApiResult

interface MissionCardRemoteDataSource {

    // 카드 리스트 반환
    suspend fun getMissionCardList(): List<MissionCardInfoEntity>

    // 카드 추가.
    suspend fun addMissionCard(missionCardInfo: MissionCardInfoEntity)

    // 카드 리스트 추가
    suspend fun addMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>)

    // 등록된 오늘의 구디 카드 반환
    suspend fun getTodayMissionCard(): MissionCardInfoEntity?

    // 즐겨찾기된 카드 리스트 반환
    suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity>

    // 즐겨찾기 등록
    suspend fun bookmarkMissionCard(position: Int)

    // 즐겨찾기 해제
    suspend fun unBookmarkMissionCard(position: Int)

    suspend fun downloadMissionCardList(): ApiResult<MissionCardDto>
}