package com.team.nineg.data.db.dto

import com.team.nineg.data.db.domain.Goody

data class GoodyDto(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val dueDate: String
)

fun GoodyDto.asDomainModel() = Goody(id, title, content, photoUrl, 0, dueDate)