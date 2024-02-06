package com.example.nineg.ui.mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import com.example.nineg.data.db.MissionCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val missionCardRepository: MissionCardRepository
) : ViewModel() {
    private val _missionCards = MutableLiveData<List<MissionCardInfoEntity>>()
    val missionCards: LiveData<List<MissionCardInfoEntity>> = _missionCards

    private val _startNavShowCase = MutableLiveData<Any>()
    val startNavShowCase: LiveData<Any> = _startNavShowCase

    init {
        addMissionCard()
    }

    fun startNavShowCase() {
        _startNavShowCase.postValue(Any())
    }

    fun addMissionCard() {
        viewModelScope.launch(Dispatchers.IO) {
            missionCardRepository.addMissionCardList(createMissionCard())
            missionCardRepository.getMissionCardList().also {
                _missionCards.postValue((it))
            }
        }
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
}