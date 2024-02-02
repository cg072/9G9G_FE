package com.example.nineg.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionCardInfo(
    val id: Int,
    val image: String,
    val level: String = "1",
    val title: String = "Title 최대 2줄로 들어갔을 때 영역입니다. 최대 영역입니다.",
    val description: String = "Body 2줄로 들어갔을 때 최대 영역입니다. Body 2줄로 들어갔을 때 최대 영역입니다.",
    var isBookmarked: Boolean = true
): Parcelable
