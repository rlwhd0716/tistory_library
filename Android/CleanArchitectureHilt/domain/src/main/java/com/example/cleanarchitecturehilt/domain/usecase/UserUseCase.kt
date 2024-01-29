package com.example.cleanarchitecturehilt.domain.usecase

import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.model.response.User
import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.util.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUseCase @Inject constructor(
    private val getUser: GetUserUseCase,
    private val addUser: AddUserUseCase
) : BaseUseCase() {
    fun getUser(request: String) = getUser.execute(request)
    fun addUser(request: User) = addUser.execute(request)
}