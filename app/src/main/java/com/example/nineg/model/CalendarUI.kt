package com.example.nineg.model

sealed class CalendarUI {
    class DayAttr(val dayAttribute: DayAttribute) : CalendarUI()
    class Date(val day: Day) : CalendarUI()
    class Feed(val day: Day) : CalendarUI()
    object EmptyDate : CalendarUI()
}
