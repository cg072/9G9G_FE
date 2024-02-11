package com.example.nineg.data.db.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionCardDtoItem(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("guideText")
    val guideText: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("level")
    val level: Int,
    @SerializedName("photo_url")
    val photoUrl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String
): Parcelable