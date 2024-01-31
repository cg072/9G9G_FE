package com.example.nineg.ui.mission

import androidx.lifecycle.ViewModel
import com.example.nineg.data.db.local.MissionCardInfo
import com.example.nineg.util.ListLiveData

class MissionViewModel : ViewModel() {

    private val testList = mutableListOf<MissionCardInfo>()
    private var _missionCards = ListLiveData<MissionCardInfo>()
    val missionCards: ListLiveData<MissionCardInfo>
        get() = _missionCards

    // 테스트 용도입니다.
    var i = 0

    fun addMissionCard() {
        testList.addAll(createMissionCard())
        _missionCards.postValue(ArrayList(testList))
    }

    private fun createMissionCard(): List<MissionCardInfo> {
        val a = arrayListOf<MissionCardInfo>()
        a.add(
            MissionCardInfo(
                id = i++,
                image = "https://i.ytimg.com/vi/RncY8aNDr8U/maxresdefault.jpg",
                title = "title1",
                description = "description1",
                isBookmarked = true
            )
        )
        a.add(
            MissionCardInfo(
                id = i++,
                image = "https://m.segye.com/content/image/2023/07/06/20230706511066.jpg",
                title = "title2",
                description = "description2",
                isBookmarked = false
            )
        )
        return a
    }

    companion object {
        private const val TAG = "MissionViewModel"
    }
}