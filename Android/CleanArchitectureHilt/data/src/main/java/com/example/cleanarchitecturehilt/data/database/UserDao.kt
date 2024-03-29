package com.example.cleanarchitecturehilt.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.cleanarchitecturehilt.data.entity.UserDB
import com.example.cleanarchitecturehilt.domain.model.response.User

@Dao
interface UserDao {
    @Query("SELECT * FROM UserDB")
    fun getAll(): List<User>

    @Query("SELECT * FROM UserDB WHERE USER_SN=:userSn")
    fun getUser(userSn: String): User

    @Query("SELECT * FROM UserDB WHERE USER_ID=:userId")
    fun getUserPassword(userId: String): User

    @Insert(onConflict = REPLACE) // PrimaryKey 중복 시, 덮어쓰기
    fun insertUser(user: UserDB)

    @Delete
    fun deleteUser(user: UserDB)

    @Query("DELETE FROM UserDB")
    fun deleteAll()
}