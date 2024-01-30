package com.example.cleanarchitecturehilt.di

import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.usecase.AddUserUseCase
import com.example.cleanarchitecturehilt.domain.usecase.GetUserUseCase
import com.example.cleanarchitecturehilt.domain.usecase.LoginUseCase
import com.example.cleanarchitecturehilt.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    /** Login */
    @Provides
    fun provideLoginUseCase(repo: UserRepo) = LoginUseCase(repo)

    /** User */
    @Provides
    fun provideGetUserUseCase(repo: UserRepo) = GetUserUseCase(repo)

    @Provides
    fun provideAddUserUseCase(repo: UserRepo) = AddUserUseCase(repo)

    /** Memo */
    @Provides
    fun provideUserUseCase(repo: UserRepo) =
        UserUseCase(
            GetUserUseCase(repo),
            AddUserUseCase(repo)
        )

}

