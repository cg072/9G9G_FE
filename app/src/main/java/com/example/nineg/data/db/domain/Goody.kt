package com.example.nineg.data.db.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Goody(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val level: Int,
    val dueDate: String
) : Parcelable