package com.example.cleanarchitecturehilt.data.repository.impl

import com.example.cleanarchitecturehilt.data.datasource.local.UserLocalDataSource
import com.example.cleanarchitecturehilt.data.datasource.remote.UserRemoteDataSource
import com.example.cleanarchitecturehilt.data.entity.UserDB
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
     */
    override suspend fun login(data: Login) = safeApiCall { remote.login(data) }

    /**
     * 사용자 등록
     * @local insert 실행
     */
    override suspend fun insertUser(data: User) =
        safeDBCall {
            local.insertUser(
                UserDB(
                    sn = data.sn,
                    id = data.sn,
                    name = data.name,
                    type = data.type,
                )
            )
        }

    /**
     * 사용자 정보 조회
     * @local select 실행
     */
    override suspend fun getUser(userSn: String) =
        safeDBCall { local.getUser(userSn) }

}