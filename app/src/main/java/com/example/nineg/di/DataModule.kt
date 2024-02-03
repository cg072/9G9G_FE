package com.example.nineg.di

import android.content.Context
import androidx.room.Room
import com.example.nineg.data.db.room.MissionCardDao
import com.example.nineg.data.db.room.MissionCardDatabase
import com.example.nineg.data.db.MissionCardRepositoryImpl
import com.example.nineg.data.db.MissionCardRepository
import com.example.nineg.data.db.local.LocalMissionCardImpl
import com.example.nineg.data.db.local.LocalMissionCardRepository
import com.example.nineg.data.db.remote.RemoteMissionCardImpl
import com.example.nineg.data.db.remote.RemoteMissionCardRepository
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
    abstract fun bindRemoteMissionCardRepository(remoteMissionCardRepository: RemoteMissionCardImpl): RemoteMissionCardRepository

    @Binds
    abstract fun bindLocalMissionCardRepository(localMissionCardRepository: LocalMissionCardImpl): LocalMissionCardRepository

    @Binds
    abstract fun bindMissionCardRepository(missionCardRepository: MissionCardRepositoryImpl): MissionCardRepository

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