package com.example.nineg.data.db.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionCardInfo(
    val id: Int,
    val image: String,
    val level: String = "1",
    val title: String = "title",
    val description: String = "description",
    val isBookmarked: Boolean = true
): Parcelable
