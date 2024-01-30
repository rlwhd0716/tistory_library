package com.example.cleanarchitecturehilt.domain.util

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

val gson = Gson()

inline fun <reified T> String.fromJson() = try {
    gson.fromJson(this, T::class.java) as T
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun <T> T.toJson() = gson.toJson(this)

fun <T> T.toJSONObject() = JSONObject(gson.toJson(this))

fun String.makeRequestBody(): RequestBody =
    this.toRequestBody("application/json".toMediaTypeOrNull())