package com.team.nineg.data.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.team.nineg.data.db.entity.MissionCardInfoEntity

@Dao
interface MissionCardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMissionCard(missionCardInfo: MissionCardInfoEntity)

    @Delete
    suspend fun deleteMissionCard(missionCardInfo: MissionCardInfoEntity)

    @Query("SELECT * FROM missionCardInfo")
    suspend fun getMissionCardList(): List<MissionCardInfoEntity>

    @Transaction
    suspend fun insertMissionCard(missionCardInfoList: List<MissionCardInfoEntity>) {
        missionCardInfoList.forEach {
            insertMissionCard(it)
        }
    }

    @Query("SELECT * FROM missionCardInfo WHERE isBookmarked = 1")
    suspend fun getBookmarkedMissionCardList(): List<MissionCardInfoEntity>

    @Query("UPDATE missionCardInfo SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun bookmarkMissionCard(id: Int, isBookmarked: Boolean)

    @Query("DELETE FROM missionCardInfo")
    suspend fun clearMissionCard() {
        getMissionCardList().forEach {
            deleteMissionCard(it)
        }
    }
}