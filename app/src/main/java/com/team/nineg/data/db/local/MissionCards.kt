package com.team.nineg.data.db.local

import android.util.Log
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.data.db.entity.MissionCardInfoEntity
import com.team.nineg.ui.mission.MissionFragment

data class MissionCards(
    private val missionCardList: MutableList<MissionCard> = mutableListOf(),
    private val bookmarkedMissionCardList: MutableList<MissionCard> = mutableListOf(),
    private var todayGoody: List<MissionCard> = mutableListOf()
) {

    private var count = 0

    // 카드 리스트 반환
    fun getMissionCardList(): List<MissionCard> {
        return todayGoody.toList() + bookmarkedMissionCardList.toList() + missionCardList.toList()
    }

    // 카드 추가.
    private fun addMissionCard(missionCardInfo: MissionCardInfoEntity) {
        MissionCard(
            id = missionCardInfo.id,
            index = count++,
            image = missionCardInfo.image,
            level = missionCardInfo.level,
            title = missionCardInfo.title,
            guide = missionCardInfo.guide,
            content = missionCardInfo.content,
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
                content = it.content,
                guide = it.guide,
            )
        }
        this.missionCardList.addAll(missionCardList)
    }

    fun addBookmarkedMissionCardList(missionCardInfoList: List<MissionCardInfoEntity>) {
        val missionCardList = missionCardInfoList.map {
            MissionCard(
                id = it.id,
                index = count++,
                image = it.image,
                level = it.level,
                title = it.title,
                guide = it.guide,
                content = it.content,
                isBookmarked = it.isBookmarked
            )
        }
        this.bookmarkedMissionCardList.addAll(missionCardList)
    }

    // 즐겨찾기 등록
    fun bookmarkMissionCard(id: Int) {
        missionCardList.find { it.id == id }?.copy(isBookmarked = true)?.let { card ->
            if (bookmarkedMissionCardList.find { it.id == id } == null) {
                bookmarkedMissionCardList.add(card)
            }

            for ((i, item) in missionCardList.withIndex()) {
                if (item.id == id) {
                    missionCardList[i] = card
                }
            }
        }
    }

    // 즐겨찾기 해제
    fun unBookmarkMissionCard(id: Int) {
        bookmarkedMissionCardList.removeAll { it.id == id }

        missionCardList.find { it.id == id }?.let { card ->
            for ((i, item) in missionCardList.withIndex()) {
                if (item.id == id) {
                    missionCardList[i] = card.copy(isBookmarked = false)
                }
            }
        }
    }

    fun updateTodayGoody(missionCard: MissionCard?) {
        todayGoody = missionCard?.let {
            it.index = count++
            listOf(it)
        } ?: emptyList()
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    companion object {
        private const val TAG = "MissionCards"
    }
}