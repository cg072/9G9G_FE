package com.example.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.nineg.R
import com.example.nineg.adapter.CalendarFilterAdapter
import com.example.nineg.databinding.DialogCalendarFilterBinding
import com.example.nineg.model.CheckFilterModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarFilterDialog(
    context: Context,
    bottomPosition: Int,
    private val calendar: Calendar,
    private val onClick: (Int, Int) -> Unit
) :
    Dialog(context, R.style.filterDialog) {

    companion object {
        private const val MIN_YEAR = 2024
    }

    private val binding: DialogCalendarFilterBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_calendar_filter,
        null,
        false
    )

    private val formatMonth = SimpleDateFormat("MMM", Locale.ENGLISH)
    private val formatYear = SimpleDateFormat("yyyy", Locale.getDefault())
    private lateinit var adapter: CalendarFilterAdapter
    private var todayMonth: Int = 0
    private var todayYear: Int = 0
    private val yearList: List<CheckFilterModel>
    private val monthList: List<CheckFilterModel>

    init {
        setContentView(binding.root)
        setupLocation(bottomPosition)

        val todayCalendar = Calendar.getInstance()
        todayYear = todayCalendar.get(Calendar.YEAR)
        todayMonth = todayCalendar.get(Calendar.MONTH) + 1

        val checkYear = calendar.get(Calendar.YEAR)
        yearList = List(todayYear - MIN_YEAR + 1) { i -> MIN_YEAR + i }.map {
            CheckFilterModel(
                it,
                it == checkYear
            )
        }

        monthList = List(12) { i -> i + 1 }.map { CheckFilterModel(it) }
        val checkMonth = calendar.get(Calendar.MONTH) + 1
        monthList.find { it.title == checkMonth }?.isCheck = true
    }

    override fun show() {
        setupBtn()
        setupRecyclerView()
        setupListener()
        super.show()
    }

    private fun setupLocation(position: Int) {
        window?.setGravity(Gravity.TOP)

        val attr = window?.attributes?.also {
            it.y = position
        }

        window?.attributes = attr
    }

    private fun setupListener() {
        binding.dialogCalendarFilterMonthBtn.setOnClickListener {
            binding.dialogCalendarFilterMonthBtn.isSelected = true
            binding.dialogCalendarFilterYearBtn.isSelected = false
            adapter.submitList(monthList)
        }

        binding.dialogCalendarFilterYearBtn.setOnClickListener {
            binding.dialogCalendarFilterMonthBtn.isSelected = false
            binding.dialogCalendarFilterYearBtn.isSelected = true
            adapter.submitList(yearList)
        }
    }

    private fun setupRecyclerView() {
        adapter = CalendarFilterAdapter {
            if (binding.dialogCalendarFilterMonthBtn.isSelected) {
                val year = calendar.get(Calendar.YEAR)
                val month = it.title - 1
                onClick(year, month)
            }

            if (binding.dialogCalendarFilterYearBtn.isSelected) {
                val year = it.title
                val month = calendar.get(Calendar.MONTH)
                onClick(year, month)
            }
            dismiss()
        }
        binding.dialogCalendarFilterRecyclerView.adapter = adapter
        adapter.submitList(monthList)
    }

    private fun setupBtn() {
        binding.dialogCalendarFilterMonthTitle.text = formatMonth.format(calendar.time)
        binding.dialogCalendarFilterYearBtn.text = formatYear.format(calendar.time)
        binding.dialogCalendarFilterMonthBtn.isSelected = true
    }
}