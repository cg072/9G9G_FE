package com.team.nineg.di

import android.content.Context
import androidx.room.Room
import com.team.nineg.data.db.*
import com.team.nineg.data.db.room.MissionCardDao
import com.team.nineg.data.db.room.MissionCardDatabase
import com.team.nineg.data.db.MissionCardRepositoryImpl
import com.team.nineg.data.db.MissionCardRepository
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
    @Binds
    abstract fun bindMissionCardRepository(missionCardRepository: MissionCardRepositoryImpl): MissionCardRepository

    @Binds
    abstract fun bindCUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindGoodyRepository(repository: GoodyRepositoryImpl): GoodyRepository

    @Binds
    abstract fun bindBookmarkRepository(repository: BookmarkRepositoryImpl): BookmarkRepository

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