package com.team.nineg.ui.mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.data.db.MissionCardRepository
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.data.db.local.MissionCards
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
    private val _backToFirstPosition = MutableLiveData<Any>()
    val backToFirstPosition: LiveData<Any> = _backToFirstPosition

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateTodayGoody()
            missionCardRepository.downloadMissionCardList()
            addMissionCard()
        }
    }

    fun addMissionCard() {
        viewModelScope.launch(Dispatchers.IO) {
            val cardList = missionCardRepository.getMissionCardPack().sortedBy { it.level }
            val bookmarkedCardList = missionCardRepository.getBookmarkedMissionCardList()
            missionCards.addMissionCardList(cardList)
            missionCards.addBookmarkedMissionCardList(bookmarkedCardList)
            updateMissionCardList()
        }
    }

    fun isFirstLaunch(): Boolean {
        return missionCardRepository.getIsFirstLaunch()
    }

    fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        missionCardRepository.setIsFirstLaunch(isFirstLaunch)
    }

    fun updateBookmarkMissionCard(cardInfo: MissionCard) {
        viewModelScope.launch(Dispatchers.IO) {

            if (cardInfo.isBookmarked) {
                missionCardRepository.unBookmarkMissionCard(cardInfo.id)
                missionCards.unBookmarkMissionCard(cardInfo.id)
            } else {
                missionCardRepository.bookmarkMissionCard(cardInfo.id)
                missionCards.bookmarkMissionCard(cardInfo.id)
            }

            updateMissionCardList()
        }
    }

    private fun updateMissionCardList() {
        _missionCardList.postValue(missionCards.getMissionCardList())
    }

    fun updateTodayGoody() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = missionCardRepository.getUserId()
            val todayMissionCard = missionCardRepository.getTodayMissionCard(userId)
            missionCards.updateTodayGoody(todayMissionCard)
            updateMissionCardList()
            _backToFirstPosition.postValue(Any())
        }
    }

    companion object {
        private const val TAG = "MissionViewModel"
    }
}