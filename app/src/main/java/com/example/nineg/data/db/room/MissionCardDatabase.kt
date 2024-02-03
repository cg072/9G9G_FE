package com.example.nineg.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nineg.data.MissionCardInfo
import com.example.nineg.data.db.room.MissionCardDao


@Database(
    entities = [MissionCardInfo::class],
    version = 1,
    exportSchema = false
)
abstract class MissionCardDatabase : RoomDatabase() {

    abstract fun MissionCardDao(): MissionCardDao
}