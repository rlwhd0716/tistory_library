package com.example.domain.repository

import com.example.domain.model.TestData
import com.example.domain.util.BaseResponse
import kr.or.koelsa.elsm.domain.util.RepoResult

interface MainRepo {
    suspend fun test(): RepoResult<BaseResponse<TestData>>
}