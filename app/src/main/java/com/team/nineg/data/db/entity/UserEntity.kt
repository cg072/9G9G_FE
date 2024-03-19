package com.team.nineg.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.team.nineg.data.db.domain.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val deviceId: String,
    val nickname: String?,
    val profileImage: String? = null
)

fun UserEntity.asDomainModel() = User(deviceId, nickname, profileImage)