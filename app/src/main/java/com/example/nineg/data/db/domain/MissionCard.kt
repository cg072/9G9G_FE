package com.example.nineg.data.db.domain
import android.os.Parcelable
import com.example.nineg.data.db.entity.MissionCardInfoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionCard(
    // 미션 카드 아이디
    val id: Int = 0,
    // 미션 카드 인덱스
    var index: Int,
    // 미션 카드 이미지
    val image: String,
    // 미션 카드 레벨
    val level: Int,
    // 제목
    val title: String,
    // 가이드
    val guide: String?,
    // 컨텐츠
    val content: String,
    // 북마크 여부
    var isBookmarked: Boolean = false
): Parcelable

fun MissionCard.asEntityModel() =
    MissionCardInfoEntity(id, image, level, title, guide, content, isBookmarked)

fun MissionCard.asGoody(today: String) =
    Goody(id.toString(),  title, content, image, level, today)