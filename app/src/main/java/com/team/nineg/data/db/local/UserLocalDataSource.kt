package com.team.nineg.data.db.local

import androidx.lifecycle.LiveData
import com.team.nineg.data.db.entity.UserEntity

interface UserLocalDataSource {
    suspend fun getUser(): UserEntity?
    suspend fun insert(user: UserEntity)
    suspend fun clear()
}