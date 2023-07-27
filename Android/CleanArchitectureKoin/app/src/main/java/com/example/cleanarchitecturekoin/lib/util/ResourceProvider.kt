package com.example.cleanarchitecturekoin.lib.util

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ResourceProvider {
    private var context: Context? = null

    fun initializeApp(context: Context?) {
        ResourceProvider.context = context
    }

    fun getString(resId: Int): String {
        return context?.getString(resId) ?: run { "" }
    }

    fun getString(resId: Int, value: String?): String {
        return context?.getString(resId, value) ?: run { "" }
    }
}

fun getString(resId: Int): String = ResourceProvider.getString(resId)

fun getString(resId: Int, value: String?): String = ResourceProvider.getString(resId, value)
