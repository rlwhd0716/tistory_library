package com.example.cleanarchitecturehilt.lib.ext

import android.util.Log
import com.example.cleanarchitecturehilt.BuildConfig
import java.text.SimpleDateFormat
import java.util.Locale

var LOG_TAG = "Clean_Architecture_Hilt"

fun logV(msg: String?, tag: String = LOG_TAG, showCallStack: Boolean = false) {
    msg?.takeIf { it.isNotEmpty() and BuildConfig.DEBUG }?.run {
        when (showCallStack) {
            true -> Log.v(tag, buildLogMsg(this), Throwable())
            false -> Log.v(tag, buildLogMsg(this))
        }
    }
}

fun logD(msg: String?, tag: String = LOG_TAG, showCallStack: Boolean = false) {
    msg?.takeIf { it.isNotEmpty() and BuildConfig.DEBUG }?.run {
        when (showCallStack) {
            true -> Log.d(tag, buildLogMsg(this), Throwable())
            false -> Log.d(tag, buildLogMsg(this))
        }
    }
}

fun logI(msg: String?, tag: String = LOG_TAG, showCallStack: Boolean = false) {
    msg?.takeIf { it.isNotEmpty() and BuildConfig.DEBUG }?.run {
        when (showCallStack) {
            true -> Log.i(tag, buildLogMsg(this), Throwable())
            false -> Log.i(tag, buildLogMsg(this))
        }
    }
}

fun logW(msg: String?, tag: String = LOG_TAG, showCallStack: Boolean = false) {
    msg?.takeIf { it.isNotEmpty() and BuildConfig.DEBUG }?.run {
        when (showCallStack) {
            true -> Log.w(tag, buildLogMsg(this), Throwable())
            false -> Log.w(tag, buildLogMsg(this))
        }
    }
}

fun logE(msg: String?, tag: String = LOG_TAG, showCallStack: Boolean = false) {
    msg?.takeIf { it.isNotEmpty() and BuildConfig.DEBUG }?.run {
        when (showCallStack) {
            true -> Log.e(tag, buildLogMsg(this), Throwable())
            false -> Log.e(tag, buildLogMsg(this))
        }
    }
}

fun buildLogMsg(message: String): String {
    val ste = Thread.currentThread().stackTrace[5]
    return StringBuilder().apply {
        append("[ (")
        append(ste.fileName)
        append(":")
        append(ste.lineNumber)
        append(")::")
        append(ste.methodName)
        append(" ] ")
        append(message)
    }.toString()
}

fun getLogMsg(message: String): String {
    val ste = Thread.currentThread().stackTrace[5]
    val messages = StringBuilder().apply {

        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)

        append("[$simpleDateFormat (")
        append(ste.fileName)
        append(":")
        append(ste.lineNumber)
        append(")::")
        append(ste.methodName)
        append(" ] ")
        append(message)
    }.toString()
    return messages
}
