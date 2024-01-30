package com.example.cleanarchitecturehilt.di

import com.example.cleanarchitecturehilt.data.repository.impl.UserRepoImpl
import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds abstract fun bindUserRepo(userRepoImpl: UserRepoImpl): UserRepo
}