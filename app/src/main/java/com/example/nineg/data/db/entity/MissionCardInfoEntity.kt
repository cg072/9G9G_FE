package com.example.nineg.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.data.db.domain.MissionCard
import com.example.nineg.data.db.dto.GoodyDto
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "missionCardInfo")
data class MissionCardInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String,
    val level: Int,
    // 제목
    val title: String,
    // 가이드
    val guide: String,
    // 상세 설명
    val content: String,
    var isBookmarked: Boolean = true
) : Parcelable

fun MissionCardInfoEntity.asDomainModel() =
    MissionCard(id, image, level, title, guide, content, isBookmarked)