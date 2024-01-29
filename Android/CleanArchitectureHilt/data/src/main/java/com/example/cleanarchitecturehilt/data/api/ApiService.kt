package com.example.cleanarchitecturehilt.data.api

import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.model.response.User
import com.example.cleanarchitecturehilt.domain.util.BaseResponseObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("account/login")
    suspend fun login(@Body body: Login):
            Response<BaseResponseObject<User>>
}