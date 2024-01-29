package com.example.cleanarchitecturehilt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleanarchitecturehilt.data.entity.UserDB
import com.example.cleanarchitecturehilt.domain.util.RoomTypeConverter

@Database(
    entities = [
        UserDB::class,
    ], version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}