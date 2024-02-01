package com.example.nineg.ui.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nineg.R
import com.example.nineg.adapter.CalendarAdapter
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentCalendarBinding
import com.example.nineg.model.CalendarUI
import com.example.nineg.model.Day
import com.example.nineg.model.DayAttribute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {

    private val viewModel: CalendarViewModel by viewModels()

    private lateinit var adapter: CalendarAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendarRecyclerView()
    }

    private fun setupCalendarRecyclerView() {
        val dayAttributeList = listOf(
            DayAttribute("일"),
            DayAttribute("월"),
            DayAttribute("화"),
            DayAttribute("수"),
            DayAttribute("목"),
            DayAttribute("금"),
            DayAttribute("토")
        ).map { CalendarUI.DayAttr(it) }

        val dayList = List(31) { i -> i + 1 }.map { CalendarUI.Date(Day(it)) }

        val list = mutableListOf<CalendarUI>()
        list.addAll(dayAttributeList)
        list.addAll(dayList)

        adapter = CalendarAdapter()
        binding.fragmentCalendarRecyclerView.adapter = adapter
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 7)
        adapter.submitList(list)
    }
}