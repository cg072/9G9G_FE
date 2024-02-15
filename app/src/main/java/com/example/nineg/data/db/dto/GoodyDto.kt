package com.example.nineg.data.db.dto

import com.example.nineg.data.db.domain.Goody
import java.util.Date

data class GoodyDto(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val dueDate: String
)

fun GoodyDto.asDomainModel() = Goody(id, title, content, photoUrl, dueDate)