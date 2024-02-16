package com.example.nineg.ui.calendar

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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: GoodyRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "CalendarViewModel"
    }

    private val _calendarUiList = MutableLiveData<List<CalendarUI>>()
    val calendarUiList: LiveData<List<CalendarUI>> get() = _calendarUiList

    private var goodyMap: Map<String, Goody> = emptyMap()
    private val format = SimpleDateFormat("yyyy-MM-", Locale.getDefault())


    fun requestGoodyList(deviceId: String, calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGoodyList(deviceId)

            when (result) {
                is ApiResult.Success -> {
                    goodyMap = result.value.associateBy { it.dueDate }
                }
                is ApiResult.Error -> {
                    result.exception?.printStackTrace()
                }
            }

            getCalendarList(calendar)
        }
    }

    fun getCalendarList(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val emptyDateSize = calendar.get(Calendar.DAY_OF_WEEK) - 1

            val cloneCalendar = calendar.clone() as Calendar
            cloneCalendar.add(Calendar.MONTH, 1)
            cloneCalendar.add(Calendar.DATE, -1)

            val dateSize = cloneCalendar.get(Calendar.DATE)

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
            val yearMonthStr = format.format(calendar.time)

            val dateList = List(dateSize) { i -> i + 1 }.map {
                val dueDate = yearMonthStr + it
                CalendarUI.Date(
                    Day(
                        it,
                        goodyMap[dueDate]
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

    fun getFeed() {
        _calendarUiList.postValue(goodyMap.values.map { CalendarUI.Feed(it) })
    }
}