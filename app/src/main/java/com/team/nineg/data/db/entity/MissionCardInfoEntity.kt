package com.team.nineg.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.team.nineg.data.db.domain.MissionCard
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "missionCardInfo")
data class MissionCardInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val image: String,
    val level: Int,
    // 제목
    val title: String,
    // 가이드
    val guide: String?,
    // 상세 설명
    val content: String,
    var isBookmarked: Boolean = false
) : Parcelable

fun MissionCardInfoEntity.asDomainModel() =
    MissionCard(id,0, image, level, title, guide, content, isBookmarked)
