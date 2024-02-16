package com.example.nineg.ui.mission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.databinding.ItemMissionCardBinding

class MissionCardAdapter(private val recyclerViewClickListener: MissionCardRecyclerViewClickListener) : ListAdapter<MissionCard, MissionCardViewHolder>(MissionCardDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionCardViewHolder {
        return MissionCardViewHolder(
            ItemMissionCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), recyclerViewClickListener
        )
    }

    override fun onBindViewHolder(holder: MissionCardViewHolder, position: Int) {
        val cardInfo = currentList[position]
        holder.bind(cardInfo)
    }

    companion object {
        private const val TAG = "MissionCardAdapter"
        private val MissionCardDiffUtil = object : DiffUtil.ItemCallback<MissionCard>() {
            override fun areItemsTheSame(oldItem: MissionCard, newItem: MissionCard): Boolean {
                return (oldItem.index == newItem.index) && (oldItem.isBookmarked == newItem.isBookmarked)
            }

            override fun areContentsTheSame(oldItem: MissionCard, newItem: MissionCard): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: MissionCard, newItem: MissionCard): Boolean {
                return oldItem.isBookmarked == newItem.isBookmarked
            }
        }
    }
}