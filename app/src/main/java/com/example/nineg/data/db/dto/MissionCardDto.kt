package com.example.nineg.data.db.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MissionCardDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("guide")
    val guid: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("level")
    val level: Int,
    @SerializedName("img")
    val imgUrl: String,
    @SerializedName("created_at")
    val createDate: Date,
    @SerializedName("updated_at")
    val updateDate: Date
) : Parcelable