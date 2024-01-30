package com.example.cleanarchitecturehilt.domain.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val id: String,
    val pw: String
): Parcelable
