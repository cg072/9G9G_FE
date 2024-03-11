package com.team.nineg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.team.nineg.R
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.databinding.ItemCalendarDateBinding
import com.team.nineg.databinding.ItemCalendarDayAttributeBinding
import com.team.nineg.databinding.ItemCalendarEmpyBinding
import com.team.nineg.databinding.ItemFeedBinding
import com.team.nineg.model.CalendarUI
import com.team.nineg.model.Day
import com.team.nineg.model.DayAttribute

class CalendarAdapter(private val onClick: (Goody) -> Unit) :
    ListAdapter<CalendarUI, RecyclerView.ViewHolder>(CalendarDiffUtil) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CalendarUI.DayAttr -> R.layout.item_calendar_day_attribute
            is CalendarUI.Date -> R.layout.item_calendar_date
            is CalendarUI.EmptyDate -> R.layout.item_calendar_empy
            is CalendarUI.Feed -> R.layout.item_feed
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
                DateViewHolder(binding, onClick)
            }
            R.layout.item_calendar_empy -> {
                val binding = ItemCalendarEmpyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EmptyViewHolder(binding)
            }
            R.layout.item_feed -> {
                val binding = ItemFeedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder(binding, onClick)
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
            is FeedViewHolder -> {
                holder.bind((getItem(position) as CalendarUI.Feed).goody)
            }
        }
    }

    class DayAttributeViewHolder(private val binding: ItemCalendarDayAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DayAttribute) {
            binding.itemCalendarDayAttributeTitle.setText(item.dayOfTheWeekRes)
        }
    }

    class DateViewHolder(
        private val binding: ItemCalendarDateBinding,
        private val onClick: (Goody) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
            binding.itemCalendarDateTitle.text = day.date.toString()
            binding.itemCalendarDateImageTitle.text = day.date.toString()
            binding.itemCalendarDateImage.clipToOutline = true

            if (day.goody == null) {
                binding.itemCalendarDateImageContainer.visibility = View.GONE
            } else {
                binding.itemCalendarDateImageContainer.visibility = View.VISIBLE
                binding.itemCalendarDateImage.load(day.goody?.photoUrl) {
                    transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
                }
            }

            binding.root.setOnClickListener {
                day.goody?.let { onClick(it) }
            }
        }

        companion object {
            private const val ROUNDED_CORNERS_VALUE = 10f
        }
    }


    class FeedViewHolder(
        private val binding: ItemFeedBinding,
        private val onClick: (Goody) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(goody: Goody) {
            binding.itemFeedImage.load(goody.photoUrl)

            binding.root.setOnClickListener {
                onClick(goody)
            }
        }
    }

    class EmptyViewHolder(binding: ItemCalendarEmpyBinding) : RecyclerView.ViewHolder(binding.root)
}

object CalendarDiffUtil : DiffUtil.ItemCallback<CalendarUI>() {
    override fun areItemsTheSame(oldItem: CalendarUI, newItem: CalendarUI): Boolean {
        if (oldItem is CalendarUI.DayAttr && newItem is CalendarUI.DayAttr) {
            return oldItem.dayAttribute == newItem.dayAttribute
        } else if (oldItem is CalendarUI.Date && newItem is CalendarUI.Date) {
            return oldItem.day.date == newItem.day.date
        } else if (oldItem is CalendarUI.Feed && newItem is CalendarUI.Feed) {
            return oldItem.goody.id == newItem.goody.id
        } else if (oldItem is CalendarUI.EmptyDate && newItem is CalendarUI.EmptyDate) {
            return oldItem == newItem
        }

        return false
    }

    override fun areContentsTheSame(oldItem: CalendarUI, newItem: CalendarUI): Boolean {
        if (oldItem is CalendarUI.DayAttr && newItem is CalendarUI.DayAttr) {
            return oldItem.dayAttribute == newItem.dayAttribute
        } else if (oldItem is CalendarUI.Date && newItem is CalendarUI.Date) {
            if (oldItem.day.date != newItem.day.date) return false

            if (oldItem.day.goody != newItem.day.goody) return false

            if (oldItem.day.goody?.id != newItem.day.goody?.id) return false

            if (oldItem.day.goody?.photoUrl != newItem.day.goody?.photoUrl) return false

            return true
        } else if (oldItem is CalendarUI.Feed && newItem is CalendarUI.Feed) {
            if (oldItem.goody != newItem.goody) return false

            if (oldItem.goody.photoUrl != newItem.goody.photoUrl) return false

            return true
        } else if (oldItem is CalendarUI.EmptyDate && newItem is CalendarUI.EmptyDate) {
            return oldItem == newItem
        }

        return false
    }
}