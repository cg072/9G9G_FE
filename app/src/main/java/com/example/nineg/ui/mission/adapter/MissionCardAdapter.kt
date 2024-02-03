package com.example.nineg.ui.mission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.nineg.data.MissionCardInfo
import com.example.nineg.databinding.ItemMissionCardBinding

class MissionCardAdapter: ListAdapter<MissionCardInfo, MissionCardViewHolder>(MissionCardDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionCardViewHolder {
        return MissionCardViewHolder(
            ItemMissionCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MissionCardViewHolder, position: Int) {
        val cardInfo = currentList[position]
        holder.bind(cardInfo)
    }

    companion object {
        private val MissionCardDiffUtil = object : DiffUtil.ItemCallback<MissionCardInfo>() {
            override fun areItemsTheSame(oldItem: MissionCardInfo, newItem: MissionCardInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MissionCardInfo, newItem: MissionCardInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}