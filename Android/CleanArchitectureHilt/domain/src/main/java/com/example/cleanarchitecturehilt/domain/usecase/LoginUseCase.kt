package com.example.cleanarchitecturehilt.domain.usecase

import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.util.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(private val repo: UserRepo) : BaseUseCase() {
    fun execute(request: Login) = setChannelFlow {
        repo.login(request)
    }

    operator fun invoke(request: Login) = setChannelFlow {
        repo.login(request)
    }
}