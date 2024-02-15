package com.example.nineg.ui.mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.data.db.MissionCardRepository
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.data.db.local.MissionCards
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val missionCardRepository: MissionCardRepository
) : ViewModel() {
    
    private val missionCards = MissionCards()
    private val _missionCardList = MutableLiveData<List<MissionCard>>()
    val missionCardList: LiveData<List<MissionCard>> = _missionCardList

    private val _startNavShowCase = MutableLiveData<Any>()
    val startNavShowCase: LiveData<Any> = _startNavShowCase

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = missionCardRepository.downloadMissionCardList()
            missionCards.addMissionCardList(result)
            updateMissionCardList()
        }
    }

    fun startTutorialNav() {
        _startNavShowCase.postValue(Any())
    }

    fun addMissionCard(missionCardList: List<MissionCardInfoEntity> = createMissionCard()) {
        missionCards.addMissionCardList(missionCardList)
        updateMissionCardList()
    }

    private fun updateMissionCardList() {
        _missionCardList.postValue(missionCards.getMissionCardList())
    }

    private fun createMissionCard(): List<MissionCardInfoEntity> {
        return listOf(
            MissionCardInfoEntity(
                image = "https://i.ytimg.com/vi/RncY8aNDr8U/maxresdefault.jpg",
                level = 1,
                title = "유나 입니다. Title 최대 2줄로 들어갔을 때 영역입니다. 최대 영역입니다.",
                guide = "Body 2줄로 들어갔을 때 최대 영역입니다. Body 2줄로 들어갔을 때 최대 영역입니다.",
                content = "유나",
                isBookmarked = false
            ),
            MissionCardInfoEntity(
                image = "https://m.segye.com/content/image/2023/07/06/20230706511066.jpg",
                level = 2,
                title = "예지 입니다.Title 최대 2줄로 들어갔을 때 영역입니다. 최대 영역입니다.",
                guide = "Body 2줄로 들어갔을 때 최대 영역입니다. Body 2줄로 들어갔을 때 최대 영역입니다.",
                content = "예지",
                isBookmarked = false
            ),
            MissionCardInfoEntity(
                image = "https://img2.sbs.co.kr/img/seditor/VD/2022/05/11/0Df1652234259596-640-0.jpg",
                level = 3,
                title = "카즈하 입니다. Title 최대 2줄로 들어갔을 때 영역입니다. 최대 영역입니다.",
                guide = "Body 2줄로 들어갔을 때 최대 영역입니다. Body 2줄로 들어갔을 때 최대 영역입니다.",
                content = "카즈하",
                isBookmarked = true
            )
        )
    }

    fun isFirstLaunch(): Boolean {
        return missionCardRepository.getIsFirstLaunch()
    }

    fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        missionCardRepository.setIsFirstLaunch(isFirstLaunch)
    }
}