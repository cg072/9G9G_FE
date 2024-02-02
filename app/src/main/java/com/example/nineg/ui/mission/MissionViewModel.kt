package com.example.nineg.ui.mission

import androidx.lifecycle.ViewModel
import com.example.nineg.data.MissionCardInfo
import com.example.nineg.util.ListLiveData

class MissionViewModel : ViewModel() {

    private val testList = mutableListOf<MissionCardInfo>()
    private var _missionCards = ListLiveData<MissionCardInfo>()
    val missionCards: ListLiveData<MissionCardInfo>
        get() = _missionCards

    // 테스트 용도입니다.
    var i = 0
    init {
        addMissionCard()
    }
    fun addMissionCard() {
        testList.addAll(createMissionCard())
        _missionCards.postValue(ArrayList(testList))
    }

    private fun createMissionCard(): List<MissionCardInfo> {
        return listOf(
            MissionCardInfo(
                id = i++,
                image = "https://i.ytimg.com/vi/RncY8aNDr8U/maxresdefault.jpg",
                isBookmarked = true
            ),
            MissionCardInfo(
                id = i++,
                image = "https://m.segye.com/content/image/2023/07/06/20230706511066.jpg",
                level = "2",
                isBookmarked = false
            ),
            MissionCardInfo(
                id = i++,
                image = "https://img2.sbs.co.kr/img/seditor/VD/2022/05/11/0Df1652234259596-640-0.jpg",
                level = "3",
                isBookmarked = false
            )
        )
    }
}