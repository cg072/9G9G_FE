package com.team.nineg.data.db.dto

import com.team.nineg.data.db.domain.User
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

fun UserDto.asDomainModel() = User(deviceId, name, age, gender)