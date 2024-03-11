package com.team.nineg.di

import com.team.nineg.data.db.local.MissionCardLocalDataSource
import com.team.nineg.data.db.local.MissionCardLocalDataSourceImpl
import com.team.nineg.data.db.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindMissionCardLocalDataSource(dataSource: MissionCardLocalDataSourceImpl): MissionCardLocalDataSource

    @Binds
    abstract fun bindMissionCardRemoteDataSource(dataSource: MissionCardRemoteDataSourceImpl): MissionCardRemoteDataSource

    @Binds
    abstract fun bindUserRemoteDataSource(dataSource: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun bindGoodyRemoteDataSource(dataSource: GoodyRemoteDataSourceImpl): GoodyRemoteDataSource

    @Binds
    abstract fun bindBookmarkRemoteDataSource(dataSource: BookmarkRemoteDataSourceImpl): BookmarkRemoteDataSource
}