package com.team.nineg.data.db.dto

import com.team.nineg.data.db.entity.MissionCardInfoEntity

class MissionCardDto : ArrayList<MissionCardDtoItem>()

fun MissionCardDto.asEntityModel(): List<MissionCardInfoEntity> {
    return map {
        MissionCardInfoEntity(
            id = it.id,
            title = it.title,
            guide = it.guideText,
            image = it.photoUrl,
            level = it.level,
            content = it.content
        )
    }
}