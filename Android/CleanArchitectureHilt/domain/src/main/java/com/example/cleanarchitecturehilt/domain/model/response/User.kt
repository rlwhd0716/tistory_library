package com.example.cleanarchitecturehilt.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("userNm")
    val name: String,
    @SerializedName("userSn")
    val sn: String,
    @SerializedName("userType")
    val type: String
): Parcelable
