package com.example.domain.usecase

import com.example.domain.model.TestData
import com.example.domain.util.BaseResponse
import com.example.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun test(): Flow<UseCaseResult<BaseResponse<TestData>>>
}