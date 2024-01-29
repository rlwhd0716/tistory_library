package com.example.cleanarchitecturehilt.lib.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

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

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return context?.getDrawable(resId)
    }

}

fun getString(resId: Int): String = ResourceProvider.getString(resId)

fun getString(resId: Int, value: String?): String = ResourceProvider.getString(resId, value)
