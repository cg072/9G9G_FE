package com.example.nineg.ui.calendar

import android.os.Bundle
import android.view.View
import com.example.nineg.R
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentCalendarBinding

class CalendarFragment: BaseFragment<FragmentCalendarBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}