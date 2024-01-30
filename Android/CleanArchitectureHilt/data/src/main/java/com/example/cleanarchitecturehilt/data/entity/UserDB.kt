package com.example.cleanarchitecturehilt.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(
    @PrimaryKey
    @ColumnInfo("USER_SN") val sn: String,
    @ColumnInfo("USER_ID") val id: String,
    @ColumnInfo("USER_NM") val name: String,
    @ColumnInfo("USER_TYPE") val type: String
)
