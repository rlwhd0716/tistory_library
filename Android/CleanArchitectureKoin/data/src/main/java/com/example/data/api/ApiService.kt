package com.example.data.api

import com.example.domain.model.TestData
import com.example.domain.util.BaseResponse
import retrofit2.Response
import retrofit2.http.POST

interface ApiService {

    @POST("")
    suspend fun getTest()
    : Response<BaseResponse<TestData>>
}