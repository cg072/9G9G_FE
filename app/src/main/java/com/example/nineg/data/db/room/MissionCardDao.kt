package com.example.nineg.data.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nineg.data.MissionCardInfo

@Dao
interface MissionCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissionCard(missionCardInfo: MissionCardInfo)

    @Delete
    suspend fun deleteMissionCard(missionCardInfo: MissionCardInfo)

    @Query("SELECT * FROM missionCardInfo")
    suspend fun getMissionCardList(): List<MissionCardInfo>

    @Transaction
    suspend fun insertMissionCard(missionCardInfoList: List<MissionCardInfo>) {
        missionCardInfoList.forEach {
            insertMissionCard(it)
        }
    }
}