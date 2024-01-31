package com.example.nineg.ui.mission.adapter

import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nineg.R
import com.example.nineg.data.db.local.MissionCardInfo
import com.example.nineg.databinding.ItemMissionCardBinding

class MissionCardViewHolder(private val binding: ItemMissionCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            val clickedPosition = adapterPosition
            val recyclerView = itemView.parent as RecyclerView
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val centerPosition = layoutManager.findFirstVisibleItemPosition() + (layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition()) / 2

            if (clickedPosition == centerPosition) {
                // 화면 중앙의 아이템을 클릭했다면 아이템 정보를 다른 프레그먼트에 전달합니다.
                Log.d(TAG, "카드 중앙 선택 ")
                // ...
            } else {
                // 화면 중앙이 아닌 아이템을 클릭했다면 해당 위치로 이동합니다.
                Log.d(TAG, "화면 이동")
                recyclerView.smoothScrollToPosition(clickedPosition)
            }
        }
    }
        
    fun bind(cardInfo: MissionCardInfo) {

        itemView.apply {
            binding.ivMissionImage.load(cardInfo.image)
            binding.ivBookmark.load(R.drawable.ic_launcher_foreground)
            if(!cardInfo.isBookmarked) binding.ivBookmark.isVisible = false
            binding.tvMissionTitle.text = cardInfo.title + cardInfo.id
            binding.tvMissionDescription.text = cardInfo.description
        }
    }
    companion object {
        private const val TAG = "MissionCardViewHolder"
    }
}