package com.example.nineg.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nineg.data.db.entity.MissionCardInfoEntity


@Database(
    entities = [MissionCardInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MissionCardDatabase : RoomDatabase() {

    abstract fun MissionCardDao(): MissionCardDao
}