package com.team.nineg.ui.mission.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.team.nineg.R
import com.team.nineg.data.db.domain.MissionCard
import com.team.nineg.databinding.ItemMissionCardBinding

class MissionCardViewHolder(
    private val binding: ItemMissionCardBinding, private val recyclerViewClickListener: MissionCardRecyclerViewClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cardInfo: MissionCard) {
        itemView.setOnClickListener {
            val clickedPosition = adapterPosition
            val recyclerView = itemView.parent as RecyclerView
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val centerPosition = layoutManager.findFirstVisibleItemPosition() + (layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition()) / 2

            if (clickedPosition == centerPosition) {
                // 화면 중앙의 아이템을 클릭했다면 아이템 정보를 다른 프레그먼트에 전달합니다.
                recyclerViewClickListener.onClickRecyclerViewItem(cardInfo)
            } else {
                // 화면 중앙이 아닌 아이템을 클릭했다면 해당 위치로 이동합니다.
                recyclerView.smoothScrollToPosition(clickedPosition)
            }
        }

        itemView.apply {
            binding.ivMissionImage.post {
                binding.ivMissionImage.load(cardInfo.image) {
                    transformations(RoundedCornersTransformation(ROUNDED_CORNERS_VALUE))
                }
            }

            if (cardInfo.isBookmarked) {
                binding.ivBookmark.load(R.drawable.ic_bookmark_on)
            } else {
                binding.ivBookmark.load(R.drawable.ic_bookmark_off)
            }

            when (cardInfo.level) {
                TODAY_GOODY -> {
                    binding.ivLevel.visibility = android.view.View.INVISIBLE
                    binding.ivBookmark.visibility = android.view.View.INVISIBLE
                }
                MISSION_LEVEL_1 -> binding.ivLevel.load(R.drawable.ic_level_1)
                MISSION_LEVEL_2 -> binding.ivLevel.load(R.drawable.ic_level_2)
                MISSION_LEVEL_3 -> binding.ivLevel.load(R.drawable.ic_level_3)
                else -> binding.ivLevel.load(R.drawable.ic_level_1)
            }

            binding.tvMissionTitle.text = cardInfo.title
            binding.tvMissionGuide.text = cardInfo.guide

            binding.ivBookmark.setOnClickListener {
                recyclerViewClickListener.onClickRecyclerViewBookMark(cardInfo)
            }
        }
    }

    companion object {
        private const val TAG = "MissionCardViewHolder"
        const val ROUNDED_CORNERS_VALUE = 30f
        const val TODAY_GOODY = 0
        const val MISSION_LEVEL_1 = 1
        const val MISSION_LEVEL_2 = 2
        const val MISSION_LEVEL_3 = 3
    }
}