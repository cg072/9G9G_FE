package com.example.nineg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nineg.R
import com.example.nineg.databinding.ItemCalendarDateBinding
import com.example.nineg.databinding.ItemCalendarDayAttributeBinding
import com.example.nineg.databinding.ItemCalendarEmpyBinding
import com.example.nineg.model.CalendarUI
import com.example.nineg.model.Day
import com.example.nineg.model.DayAttribute

class CalendarAdapter :
    ListAdapter<CalendarUI, RecyclerView.ViewHolder>(CalendarDiffUtil) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CalendarUI.DayAttr -> R.layout.item_calendar_day_attribute
            is CalendarUI.Date -> R.layout.item_calendar_date
            is CalendarUI.EmptyDate -> R.layout.item_calendar_empy
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_calendar_day_attribute -> {
                val binding = ItemCalendarDayAttributeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DayAttributeViewHolder(binding)
            }

            R.layout.item_calendar_date -> {
                val binding = ItemCalendarDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DateViewHolder(binding)
            }
            R.layout.item_calendar_empy -> {
                val binding = ItemCalendarEmpyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EmptyViewHolder(binding)
            }
            else -> throw RuntimeException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayAttributeViewHolder -> {
                holder.bind((getItem(position) as CalendarUI.DayAttr).dayAttribute)
            }
            is DateViewHolder -> {
                holder.bind((getItem(position) as CalendarUI.Date).day)
            }
        }
    }

    class DayAttributeViewHolder(private val binding: ItemCalendarDayAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DayAttribute) {
            binding.itemCalendarDayAttributeTitle.setText(item.dayOfTheWeekRes)
        }
    }

    class DateViewHolder(private val binding: ItemCalendarDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
            binding.itemCalendarDateTitle.text = day.date.toString()
        }
    }

    class EmptyViewHolder(binding: ItemCalendarEmpyBinding): RecyclerView.ViewHolder(binding.root)
}

object CalendarDiffUtil : DiffUtil.ItemCallback<CalendarUI>() {
    override fun areItemsTheSame(oldItem: CalendarUI, newItem: CalendarUI): Boolean {
        if (oldItem is CalendarUI.DayAttr && newItem is CalendarUI.DayAttr) {
            return oldItem.dayAttribute == newItem.dayAttribute
        } else if (oldItem is CalendarUI.Date && newItem is CalendarUI.Date) {
            return oldItem.day.date == newItem.day.date
        } else if (oldItem is CalendarUI.EmptyDate && newItem is CalendarUI.EmptyDate) {
            return oldItem == newItem
        }

        return false
    }

    override fun areContentsTheSame(oldItem: CalendarUI, newItem: CalendarUI): Boolean {
        if (oldItem is CalendarUI.DayAttr && newItem is CalendarUI.DayAttr) {
            return oldItem.dayAttribute == newItem.dayAttribute
        } else if (oldItem is CalendarUI.Date && newItem is CalendarUI.Date) {
            return oldItem.day.date == newItem.day.date
        } else if (oldItem is CalendarUI.EmptyDate && newItem is CalendarUI.EmptyDate) {
            return oldItem == newItem
        }

        return false
    }
}