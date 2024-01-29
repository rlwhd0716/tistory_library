package com.example.cleanarchitecturehilt.domain.util

import android.os.Parcelable
import com.example.cleanarchitecturehilt.domain.code.NetworkCode
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Array 형태로 리턴받음
 * @sample 'job/list'
 */
@Parcelize
data class BaseResponse<T : Parcelable>(
    @SerializedName("RES_CD")
    val code: String = "",
    @SerializedName("RES_MSG")
    val message: String = "",
    @SerializedName("RES_SIZE")
    val size: String? = "",
    @SerializedName("RES_DATA")
    val result: List<T> = listOf(),
) : Parcelable {
    fun isSuccess(): Boolean = (code == NetworkCode.SUCCESS.code)
    fun isDatabase(): Boolean = (code == NetworkCode.QUERY.code)
}

/**
 * Object 형태로 리턴받음
 * @sample 'account/login'
 */
@Parcelize
data class BaseResponseObject<T : Parcelable>(
    @SerializedName("RES_CD")
    val code: String = "",
    @SerializedName("RES_MSG")
    val message: String = "",
    @SerializedName("RES_SIZE")
    val size: String? = "",
    @SerializedName("RES_DATA")
    val result: T
) : Parcelable {
    fun isSuccess(): Boolean = (code == NetworkCode.SUCCESS.code)
    fun isDatabase(): Boolean = (code == NetworkCode.QUERY.code)
}

/**
 * String List 형태로 리턴받음
 */
@Parcelize
data class BaseResponseString(
    @SerializedName("RES_CD")
    val code: String = "",
    @SerializedName("RES_MSG")
    val message: String = "",
    @SerializedName("RES_SIZE")
    val size: String? = "",
    @SerializedName("RES_DATA")
    val result: List<String>
) : Parcelable {
    fun isSuccess(): Boolean = (code == NetworkCode.SUCCESS.code)
    fun isDatabase(): Boolean = (code == NetworkCode.QUERY.code)
}

@Parcelize
data class BaseResponsePair<F, S>(
    @SerializedName("RES_CD")
    val code: String = "",
    @SerializedName("RES_MSG")
    val message: String = "",
    @SerializedName("RES_SIZE")
    val size: String? = "",
    @SerializedName("RES_DATA")
    val result: Pair<F, S>
) : Parcelable {
    fun isSuccess(): Boolean = (code == NetworkCode.SUCCESS.code)
    fun isDatabase(): Boolean = (code == NetworkCode.QUERY.code)
}