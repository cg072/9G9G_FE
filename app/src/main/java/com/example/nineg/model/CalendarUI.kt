package com.example.nineg.model

import com.example.nineg.data.db.domain.Goody

sealed class CalendarUI {
    class DayAttr(val dayAttribute: DayAttribute) : CalendarUI()
    class Date(val day: Day) : CalendarUI()
    class Feed(val goody: Goody) : CalendarUI()
    object EmptyDate : CalendarUI()
}
