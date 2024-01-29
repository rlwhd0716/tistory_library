package com.example.cleanarchitecturehilt.data.repository.impl

import com.example.cleanarchitecturehilt.data.datasource.local.UserLocalDataSource
import com.example.cleanarchitecturehilt.data.datasource.remote.UserRemoteDataSource
import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.model.response.User
import com.example.cleanarchitecturehilt.domain.repository.UserRepo
import com.example.cleanarchitecturehilt.domain.util.safeApiCall
import com.example.cleanarchitecturehilt.domain.util.safeDBCall
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: UserLocalDataSource
) : UserRepo {

    /**
     * 로그인 서비스
     * @remote 서버와 통신으로 로그인 서비스 실행
     * @local 네트워크가 불가능한 상황일때 실행. 단, 1회 이상 로그인을 성공했던 계정에 한 해 임시 로그인 가능함.
     */
    override suspend fun login(available: Boolean, data: Login) =
        if (available) safeApiCall { remote.login(data) }
        else safeDBCall { local.localLogin(data) }

    /**
     * 사용자 등록
     * @local insert 실행
     */
    override suspend fun insertUser(data: User) =
        safeDBCall { local.insertUser(data) }

    /**
     * 사용자 정보 조회
     * @local select 실행
     */
    override suspend fun getUser(data: String) =
        safeDBCall { local.getUser(data) }

}