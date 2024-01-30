package com.example.cleanarchitecturehilt.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(
    @PrimaryKey
    val sn: String,
    val id: String,
    val name: String,
    val type: String
)
