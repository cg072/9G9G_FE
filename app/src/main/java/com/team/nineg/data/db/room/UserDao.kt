package com.team.nineg.data.db.room

import androidx.room.*
import com.team.nineg.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun clear()
}