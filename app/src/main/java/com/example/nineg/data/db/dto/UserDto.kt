package com.example.nineg.data.db.dto

import java.util.*

data class UserDto(
    val id: Int,
    val deviceId: String?,
    val name: String?,
    val age: Int?,
    val gender: String?,
    val createdAt: Date?,
    val updatedAt: Date?
)