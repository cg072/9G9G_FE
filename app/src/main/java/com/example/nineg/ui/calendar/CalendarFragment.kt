package com.example.nineg.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nineg.R
import com.example.nineg.adapter.CalendarAdapter
import com.example.nineg.base.BaseFragment
import com.example.nineg.databinding.FragmentCalendarBinding
import com.example.nineg.dialog.CalendarFilterDialog
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
    private lateinit var calendar: Calendar
    private val format = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())

    override val layoutResourceId: Int
        get() = R.layout.fragment_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = Calendar.getInstance()
        binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)

        binding.fragmentCalendarDateFilterContainer.setOnClickListener {
            val dialog = CalendarFilterDialog(
                binding.root.context,
                it.bottom,
                calendar.clone() as Calendar
            ) { year, month ->
                calendar.set(year, month, 1)
                binding.fragmentCalendarDateFilterTitle.text = format.format(calendar.time)
                adapter.submitList(viewModel.getCalendarList(calendar))
            }

            dialog.show()
        }

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