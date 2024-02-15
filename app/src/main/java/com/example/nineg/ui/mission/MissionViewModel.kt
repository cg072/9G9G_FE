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
            missionCardRepository.downloadMissionCardList()
            addMissionCard()
        }
    }

    fun startTutorialNav() {
        _startNavShowCase.postValue(Any())
    }

    fun addMissionCard() {
        viewModelScope.launch(Dispatchers.IO) {
            val cardList = missionCardRepository.getMissionCardPack().sortedBy { it.level }
            missionCards.addMissionCardList(cardList)
            updateMissionCardList()
        }
    }

    private fun updateMissionCardList() {
        _missionCardList.postValue(missionCards.getMissionCardList())
    }

    fun isFirstLaunch(): Boolean {
        return missionCardRepository.getIsFirstLaunch()
    }

    fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        missionCardRepository.setIsFirstLaunch(isFirstLaunch)
    }
}