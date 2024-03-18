package com.team.nineg.di

import com.team.nineg.data.db.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}