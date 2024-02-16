package com.example.nineg.ui.mission.adapter

import com.example.nineg.data.db.domain.MissionCard

interface MissionCardRecyclerViewClickListener {
    fun onRecyclerViewBookMarkClick(cardInfo: MissionCard)
}