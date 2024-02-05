package com.example.cleanarchitecturehilt.domain.repository

import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.model.response.User
import com.example.cleanarchitecturehilt.domain.util.BaseResponseObject
import com.example.cleanarchitecturehilt.domain.util.RepoResult

interface UserRepo {
    suspend fun login(data: Login): RepoResult<BaseResponseObject<User>>
    suspend fun insertUser(data: User): RepoResult<BaseResponseObject<User>>
    suspend fun getUser(userSn: String): RepoResult<BaseResponseObject<User>>
}