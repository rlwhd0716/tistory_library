package com.example.cleanarchitecturehilt.domain.usecase

import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.util.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase @Inject constructor(private val repo: UserRepo) : BaseUseCase() {
    fun execute(userSn: String) = setChannelFlow {
        repo.getUser(userSn)
    }

    operator fun invoke(userSn: String) = setChannelFlow {
        repo.getUser(userSn)
    }
}