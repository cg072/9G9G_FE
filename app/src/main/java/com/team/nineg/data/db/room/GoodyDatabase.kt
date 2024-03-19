package com.team.nineg.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team.nineg.data.db.entity.MissionCardInfoEntity
import com.team.nineg.data.db.entity.UserEntity


@Database(
    entities = [MissionCardInfoEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class GoodyDatabase : RoomDatabase() {

    abstract fun getMissionCardDao(): MissionCardDao

    abstract fun getUserDao(): UserDao
}