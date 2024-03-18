package com.team.nineg.data.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.team.nineg.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun insert(users: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun clear()
}