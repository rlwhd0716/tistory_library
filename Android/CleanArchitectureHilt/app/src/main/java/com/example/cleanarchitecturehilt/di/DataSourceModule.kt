package com.example.cleanarchitecturehilt.di

import com.example.cleanarchitecturehilt.data.api.ApiService
import com.example.cleanarchitecturehilt.data.database.UserDao
import com.example.cleanarchitecturehilt.data.datasource.local.UserLocalDataSource
import com.example.cleanarchitecturehilt.data.datasource.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    /** Remote */
    @Provides
    fun provideUserRemoteDataSource(api: ApiService) = UserRemoteDataSource(api)

    @Provides
    fun provideUserLocalDataSource(dao: UserDao) = UserLocalDataSource(dao)
}