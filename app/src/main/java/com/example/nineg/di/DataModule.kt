package com.example.nineg.di

import android.content.Context
import androidx.room.Room
import com.example.nineg.data.db.*
import com.example.nineg.data.db.room.MissionCardDao
import com.example.nineg.data.db.room.MissionCardDatabase
import com.example.nineg.data.db.local.MissionCardLocalDataSourceImpl
import com.example.nineg.data.db.local.MissionCardLocalDataSource
import com.example.nineg.data.db.remote.MissionCardRemoteDataSourceImpl
import com.example.nineg.data.db.remote.MissionCardRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    // 저장소

    @Binds
    abstract fun bindMissionCardRepository(missionCardRepository: MissionCardRepositoryImpl): MissionCardRepository

    @Binds
    abstract fun bindCalendarRepository(repository: CalendarRepositoryImpl): CalendarRepository

    @Binds
    abstract fun bindGoodyRepository(repository: GoodyRepositoryImpl): GoodyRepository

    companion object {

        // Room
        @Provides
        @Singleton
        fun provideMissionCardDatabase(@ApplicationContext context: Context): MissionCardDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MissionCardDatabase::class.java,
                "missionCard.db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideMissionCardDao(
            missionCardDatabase: MissionCardDatabase
        ): MissionCardDao {
            return missionCardDatabase.MissionCardDao()
        }
    }
}