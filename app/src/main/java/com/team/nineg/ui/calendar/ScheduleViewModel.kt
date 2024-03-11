package com.team.nineg.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.R
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.model.CalendarUI
import com.team.nineg.model.Day
import com.team.nineg.model.DayAttribute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor() : ViewModel() {

    private val _calendarUiList = MutableLiveData<List<CalendarUI>>()
    val calendarUiList: LiveData<List<CalendarUI>> get() = _calendarUiList

    private var goodyMap: Map<String, Goody> = emptyMap()
    private val format = SimpleDateFormat("yyyy-MM-", Locale.getDefault())

    fun setGoodyMap(map: Map<String, Goody>) {
        goodyMap = map
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
}