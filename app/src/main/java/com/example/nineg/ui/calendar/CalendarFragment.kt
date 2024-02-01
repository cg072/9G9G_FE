package com.example.nineg.ui.calendar

import android.os.Bundle
import android.util.Log
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {

    companion object {
        private const val TAG = "CalendarFragment"
    }

    private val viewModel: CalendarViewModel by viewModels()

    private lateinit var adapter: CalendarAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar: Calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
        binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)

        setupCalendarRecyclerView()
        adapter.submitList(viewModel.getCalendarList(calendar))
    }

    private fun setupCalendarRecyclerView() {
        adapter = CalendarAdapter()
        binding.fragmentCalendarRecyclerView.adapter = adapter
        binding.fragmentCalendarRecyclerView.layoutManager =
            GridLayoutManager(binding.root.context, 7)
    }
}