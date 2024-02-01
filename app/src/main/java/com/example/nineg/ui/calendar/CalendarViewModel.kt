package com.example.nineg.ui.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nineg.R
import com.example.nineg.model.CalendarUI
import com.example.nineg.model.Day
import com.example.nineg.model.DayAttribute
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "CalendarViewModel"
    }

    fun getCalendarList(calendar: Calendar): List<CalendarUI> {
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
        val dateList = List(dateSize) { i -> i + 1 }.map { CalendarUI.Date(Day(it)) }

        return buildList {
            addAll(dayAttributeList)
            addAll(emptyDateList)
            addAll(dateList)
        }
    }

    private fun Calendar.printDateFormat() {
        val format = SimpleDateFormat("yyyy MM월 dd일", Locale.getDefault())
        Log.d(TAG, "kch calendar date format : ${format.format(this.time)}")
    }
}