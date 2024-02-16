package com.example.nineg.data.db.local

import android.util.Log
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.data.db.entity.MissionCardInfoEntity

data class MissionCards(private val missionCardList: MutableList<MissionCard> = mutableListOf(), private val bookmarkedMissionCardList: MutableList<MissionCard> = mutableListOf()) {

    private var count = 0

    // 카드 리스트 반환
    fun getMissionCardList(): List<MissionCard> {
        val a = arrayListOf<MissionCard>()
        a.addAll( bookmarkedMissionCardList + missionCardList)
        Log.d(TAG, "getMissionCardList: missionCardList - ${System.identityHashCode(missionCardList)}")
        Log.d(TAG, "getMissionCardList: a ->  ${System.identityHashCode(a)}")
        return a.toList()
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
                isBookmarked = it.isBookmarked
            )
        }
        this.bookmarkedMissionCardList.addAll(missionCardList)
    }

    // 즐겨찾기 등록
    fun bookmarkMissionCard(id: Int) {
        Log.d(TAG, "bookmarkMissionCard 1: ${missionCardList.filter { it.id == id }[0]}")
        missionCardList.filter { it.id == id }.forEach {
            it.isBookmarked = true
        }
        Log.d(TAG, "bookmarkMissionCard 2: ${missionCardList.filter { it.id == id }[0]}")
        bookmarkedMissionCardList.add(missionCardList.filter { it.id == id }[0])
    }

    // 즐겨찾기 해제
    fun unBookmarkMissionCard(id: Int) {
        Log.d(TAG, "unBookmarkMissionCard 1: ${missionCardList.filter { it.id == id }[0]}")

        missionCardList.filter { it.id == id }.forEach {
            it.isBookmarked = false
        }
        Log.d(TAG, "unBookmarkMissionCard 1: ${missionCardList.filter { it.id == id }[0]}")
        bookmarkedMissionCardList.remove(missionCardList.filter { it.id == id }[0])
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    companion object {
        private const val TAG = "MissionCards"
    }
}