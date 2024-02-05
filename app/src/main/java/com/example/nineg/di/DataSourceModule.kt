package com.example.nineg.di

import com.example.nineg.data.db.remote.UserRemoteDataSource
import com.example.nineg.data.db.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindUserRemoteDataSource(dataSource: UserRemoteDataSourceImpl): UserRemoteDataSource
}