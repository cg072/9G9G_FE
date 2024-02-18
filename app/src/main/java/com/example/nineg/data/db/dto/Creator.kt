package com.example.nineg.data.db.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(
    @SerializedName("age")
    val age: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
): Parcelable
