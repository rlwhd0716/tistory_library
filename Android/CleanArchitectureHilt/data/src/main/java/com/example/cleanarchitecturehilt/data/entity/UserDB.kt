package com.example.cleanarchitecturehilt.data.entity

import androidx.room.Entity

@Entity
data class UserDB(
    val id: String,
    val sn: String,
    val name: String,
    val type: String
)
