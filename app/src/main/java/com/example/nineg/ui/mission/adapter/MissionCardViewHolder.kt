package com.example.nineg.ui.mission.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nineg.R
import com.example.nineg.data.db.local.MissionCardInfo
import com.example.nineg.databinding.ItemMissionCardBinding

class MissionCardViewHolder(private val binding: ItemMissionCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cardInfo: MissionCardInfo) {

        itemView.apply {
            binding.ivMissionImage.load(cardInfo.image)
            binding.ivBookmark.load(R.drawable.ic_launcher_foreground)
            if(!cardInfo.isBookmarked) binding.ivBookmark.isVisible = false
            binding.tvMissionTitle.text = cardInfo.title
            binding.tvMissionDescription.text = cardInfo.description
        }
    }
}