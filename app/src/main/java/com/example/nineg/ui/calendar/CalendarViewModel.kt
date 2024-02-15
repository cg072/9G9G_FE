package com.example.nineg.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.R
import com.example.nineg.data.db.GoodyRepository
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.model.CalendarUI
import com.example.nineg.model.Day
import com.example.nineg.model.DayAttribute
import com.example.nineg.retrofit.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: GoodyRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "CalendarViewModel"
    }

    private val _calendarUiList = MutableLiveData<List<CalendarUI>>()
    val calendarUiList: LiveData<List<CalendarUI>> get() = _calendarUiList

    private val _goodyList = MutableLiveData<List<Goody>>()
    val goodyList: LiveData<List<Goody>> get() = _goodyList


    fun requestGoodyList(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGoodyList(deviceId)

            when (result) {
                is ApiResult.Success -> {
                    _goodyList.postValue(result.value!!)
                }
                is ApiResult.Error -> {
                    _goodyList.postValue(emptyList())
                    result.exception?.printStackTrace()
                }
            }
        }
    }

    fun getCalendarList(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val emptyDateSize = calendar.get(Calendar.DAY_OF_WEEK) - 1
            calendar.printDateFormat()

            val cloneCalendar = calendar.clone() as Calendar
            cloneCalendar.add(Calendar.MONTH, 1)
            cloneCalendar.add(Calendar.DATE, -1)

            val dateSize = cloneCalendar.get(Calendar.DATE)
            cloneCalendar.printDateFormat()

            val dayAttributeList = listOf(
                DayAttribute(R.string.sunday),
                DayAttribute(R.string.monday),
                DayAttribute(R.string.tuesday),
                DayAttribute(R.string.wednesday),
                DayAttribute(R.string.thursday),
                DayAttribute(R.string.friday),
                DayAttribute(R.string.saturday)
            ).map { CalendarUI.DayAttr(it) }

            val emptyDateList = List(emptyDateSize) {}.map { CalendarUI.EmptyDate }
            val dateList = List(dateSize) { i -> i + 1 }.map {
                CalendarUI.Date(
                    Day(
                        it,
                        if (it % 2 == 0) "1" else "" // Image input
                    )
                )
            }

            _calendarUiList.postValue(
                buildList {
                    addAll(dayAttributeList)
                    addAll(emptyDateList)
                    addAll(dateList)
                }
            )
        }
    }

    private fun Calendar.printDateFormat() {
        val format = SimpleDateFormat("yyyy MM월 dd일", Locale.getDefault())
        Log.d(TAG, "kch calendar date format : ${format.format(this.time)}")
    }

    fun getFeed() {
        val feedList = List(4) { i -> i + 1 }.map {
            CalendarUI.Feed(
                Day(
                    it,
                    "https://m.segye.com/content/image/2023/07/06/20230706511066.jpg"
                )
            )
        }
        _calendarUiList.postValue(feedList)
    }
}