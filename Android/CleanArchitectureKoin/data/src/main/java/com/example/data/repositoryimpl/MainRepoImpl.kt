package com.example.data.repositoryimpl

import android.util.Log
import com.example.data.BaseRepo
import com.example.data.api.ApiService
import com.example.domain.repository.MainRepo
import com.example.domain.util.safeApiCall

class MainRepoImpl(private val service: ApiService): BaseRepo(), MainRepo {
    override suspend fun test() = safeApiCall {
        Log.e(TAG, "getSidoCode:::")
        service.getTest()
    }

}