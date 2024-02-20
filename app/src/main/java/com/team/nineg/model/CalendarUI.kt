package com.team.nineg.model

import com.team.nineg.data.db.domain.Goody

sealed class CalendarUI {
    class DayAttr(val dayAttribute: DayAttribute) : CalendarUI()
    class Date(val day: Day) : CalendarUI()
    class Feed(val goody: Goody) : CalendarUI()
    object EmptyDate : CalendarUI()
}
