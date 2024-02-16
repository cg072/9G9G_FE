package com.example.nineg.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.model.CalendarUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {

    private val _calendarUiList = MutableLiveData<List<CalendarUI>>()
    val calendarUiList: LiveData<List<CalendarUI>> get() = _calendarUiList

    private var goodyMap: Map<String, Goody> = emptyMap()

    fun setGoodyMap(map: Map<String, Goody>) {
        goodyMap = map
    }

    fun getFeed() {
        _calendarUiList.postValue(goodyMap.values.map { CalendarUI.Feed(it) })
    }
}