package com.example.cleanarchitecturehilt.data.datasource.remote

import com.example.cleanarchitecturehilt.data.api.ApiService
import com.example.cleanarchitecturehilt.domain.model.request.Login
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val api: ApiService) {
    suspend fun login(data: Login) = api.login(data)

}