package com.example.cleanarchitecturehilt.data.datasource.local

import android.os.Parcelable
import com.example.cleanarchitecturehilt.data.database.UserDao
import com.example.cleanarchitecturehilt.domain.code.NetworkCode
import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.model.response.User
import com.example.cleanarchitecturehilt.domain.util.BaseResponse
import com.example.cleanarchitecturehilt.domain.util.BaseResponseObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(private val dao: UserDao) {
    suspend fun getAllUser() = BaseResponse(
        code = NetworkCode.QUERY.code,
        message = "사용자 전체 조회 완료",
        result = dao.getAll()
    )

    suspend fun getUser(employerSn: String) = BaseResponseObject(
        code = NetworkCode.QUERY.code,
        message = "사용자 정보 조회 완료",
        result = dao.getUser(employerSn)
    )

    suspend fun insertUser(data: User): BaseResponseObject<User> {
        dao.insertUser(data)
        return BaseResponseObject(
            code = NetworkCode.QUERY.code,
            message = "로그인 성공",
            result = dao.getUser(data.sn)
        )
    }

    suspend fun deleteUser(data: User): BaseResponse<Parcelable> {
        dao.deleteUser(data)
        return BaseResponse(
            code = NetworkCode.QUERY.code,
            message = "사용자 삭제 완료",
        )
    }

    suspend fun deleteAll(): BaseResponse<Parcelable> {
        dao.deleteAll()
        return BaseResponse(
            code = NetworkCode.QUERY.code,
            message = "전체 삭제 완료",
        )
    }

    suspend fun localLogin(data: Login) = BaseResponseObject(
        code = NetworkCode.QUERY.code,
        message = "임시 로그인 되었습니다. 일부 기능이 제한됩니다.",
        result = dao.getUserPassword(data.id)
//        result = dao.getUserPassword(data.emplyrId, data.emplyrPassword)
    )
}