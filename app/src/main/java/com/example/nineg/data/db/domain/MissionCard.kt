package com.example.nineg.data.db.domain

import android.os.Parcelable
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionCard(
    val id: Int = 0,
    val image: String,
    val level: Int,
    val title: String,
    val guide: String,
    val content: String,
    var isBookmarked: Boolean = true
) : Parcelable

fun MissionCard.asEntityModel() =
    MissionCardInfoEntity(id, image, level, title, guide, content, isBookmarked)