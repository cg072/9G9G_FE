package com.team.nineg.data.db.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.team.nineg.data.db.entity.UserEntity
import com.team.nineg.data.db.entity.asDomainModel
import com.team.nineg.data.db.room.UserDao
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val userDao: UserDao): UserLocalDataSource {
    override suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }

    override suspend fun insert(user: UserEntity) {
        return userDao.insert(user)
    }

    override suspend fun clear() {
        return userDao.clear()
    }
}






















