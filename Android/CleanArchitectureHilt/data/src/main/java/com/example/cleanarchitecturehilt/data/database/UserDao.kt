package com.example.cleanarchitecturehilt.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.cleanarchitecturehilt.domain.model.response.User

@Dao
interface UserDao {
    @Query("SELECT * FROM UserDB")
    fun getAll(): List<User>

    @Query("SELECT * FROM UserDB WHERE sn=:data")
    fun getUser(data: String): User

    @Query("SELECT * FROM UserDB WHERE id=:id")
    fun getUserPassword(id: String): User

    @Insert(onConflict = REPLACE) // PrimaryKey 중복 시, 덮어쓰기
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM UserDB")
    fun deleteAll()
}