package com.team.nineg.ui.mission.adapter

import com.team.nineg.data.db.domain.MissionCard

interface MissionCardRecyclerViewClickListener {
    fun onClickRecyclerViewBookMark(cardInfo: MissionCard)

    fun onClickRecyclerViewItem(cardInfo: MissionCard)
}