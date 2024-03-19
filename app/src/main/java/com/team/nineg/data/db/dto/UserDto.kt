package com.team.nineg.data.db.dto

import com.team.nineg.data.db.domain.User
import com.team.nineg.data.db.entity.UserEntity
import java.util.Date

data class UserDto(
    val id: Int,
    val deviceId: String?,
    val nickname: String?,
    val profileImageUrl: String?,
    val createdAt: Date?,
    val updatedAt: Date?
)

fun UserDto.asDomainModel() = User(deviceId!!, nickname, profileImageUrl)
fun UserDto.asEntityModel() = UserEntity(deviceId!!, nickname, profileImageUrl)