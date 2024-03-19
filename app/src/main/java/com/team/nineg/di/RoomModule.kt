package com.team.nineg.di

import android.content.Context
import androidx.room.Room
import com.team.nineg.data.db.room.MissionCardDao
import com.team.nineg.data.db.room.GoodyDatabase
import com.team.nineg.data.db.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "goody.db"

    @Singleton
    @Provides
    fun provideScoreDatabase(@ApplicationContext context: Context): GoodyDatabase {
        return Room.databaseBuilder(context, GoodyDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideMissionCardDao(database: GoodyDatabase): MissionCardDao {
        return database.getMissionCardDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: GoodyDatabase): UserDao {
        return database.getUserDao()
    }
}