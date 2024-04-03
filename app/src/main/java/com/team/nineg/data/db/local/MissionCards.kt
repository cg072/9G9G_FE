package com.team.nineg.data.db.local

import android.util.Log
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.data.db.entity.MissionCardInfoEntity

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
        val newItem = missionCardList.filter { it.id == id }[0].copy(isBookmarked = true)

        for ((i, missionCard) in missionCardList.withIndex()) {
            if (missionCard.id == id) {
                missionCardList[i] = newItem
            }
        }

        bookmarkedMissionCardList.add(missionCardList.filter { it.id == id }[0])
    }

    // 즐겨찾기 해제
    fun unBookmarkMissionCard(id: Int) {
        bookmarkedMissionCardList.remove(missionCardList.filter { it.id == id }[0])

        val newItem = missionCardList.filter { it.id == id }[0].copy(isBookmarked = false)

        for ((i, missionCard) in missionCardList.withIndex()) {
            if (missionCard.id == id) {
                missionCardList[i] = newItem
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