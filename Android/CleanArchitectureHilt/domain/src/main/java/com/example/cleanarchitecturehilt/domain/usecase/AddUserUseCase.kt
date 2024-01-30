package com.example.cleanarchitecturehilt.domain.usecase

import com.example.cleanarchitecturehilt.domain.model.response.User
import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.util.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddUserUseCase @Inject constructor(private val repo: UserRepo) : BaseUseCase() {
    fun execute(user: User) = setChannelFlow {
        repo.insertUser(user)
    }

    operator fun invoke(user: User) = setChannelFlow {
        repo.insertUser(user)
    }
}