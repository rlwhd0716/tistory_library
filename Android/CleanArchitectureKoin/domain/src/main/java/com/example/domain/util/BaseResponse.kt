package com.example.domain.util

import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.Interceptor
import okhttp3.Response

@Parcelize
data class BaseResponse<T : Parcelable>(
    @SerializedName("resultCode")
    val code: String,

    @SerializedName("resultData")
    val result: List<T> = listOf(),

    @SerializedName("resultMsg")
    val message: String? = "",

    @SerializedName("resultSize")
    val size: Int = 0,

    @SerializedName("totalSize")
    val totalSize: Int = 0
) : Parcelable, Interceptor {

    fun getFirst(): T = result[0]
    override fun intercept(chain: Interceptor.Chain): Response {
        val filename = chain.request().url.pathSegments.last()
        Log.i("intercept ::: ", filename)
        return Response.Builder().build()
    }
}

