package com.team.nineg.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.team.nineg.R
import com.team.nineg.adapter.CalendarFilterAdapter
import com.team.nineg.databinding.DialogCalendarFilterBinding
import com.team.nineg.model.CheckFilterModel
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
                it.toString(),
                it == checkYear
            )
        }

        monthList = List(12) { i -> i + 1 }.map {
            val titleRes = when (it) {
                1 -> R.string.january
                2 -> R.string.february
                3 -> R.string.march
                4 -> R.string.april
                5 -> R.string.may
                6 -> R.string.june
                7 -> R.string.july
                8 -> R.string.august
                9 -> R.string.september
                10 -> R.string.october
                11 -> R.string.november
                12 -> R.string.december
                else -> R.string.app_name
            }

            CheckFilterModel(it, context.getString(titleRes))
        }
        val checkMonth = calendar.get(Calendar.MONTH) + 1
        monthList.find { it.data == checkMonth }?.isCheck = true
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
                val month = it.data - 1
                onClick(year, month)
            }

            if (binding.dialogCalendarFilterYearBtn.isSelected) {
                val year = it.data
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