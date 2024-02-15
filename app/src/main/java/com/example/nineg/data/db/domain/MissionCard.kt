package com.example.nineg.data.db.domain

data class MissionCard(
    // 미션 카드 아이디
    val id: Int = 0,
    // 미션 카드 인덱스
    val index: Int,
    // 미션 카드 이미지
    val image: String,
    // 미션 카드 레벨
    val level: Int,
    // 제목
    val title: String,
    // 가이드
    val subTitle: String?,
    // 북마크 여부
    var isBookmarked: Boolean = false
)