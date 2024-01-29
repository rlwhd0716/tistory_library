package com.example.cleanarchitecturehilt.domain.usecase

import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.util.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(private val repo: UserRepo) : BaseUseCase() {
    fun execute(available: Boolean, request: Login) = setChannelFlow {
        repo.login(available, request)
    }

    operator fun invoke(available: Boolean, request: Login) = setChannelFlow {
        repo.login(available, request)
    }
}