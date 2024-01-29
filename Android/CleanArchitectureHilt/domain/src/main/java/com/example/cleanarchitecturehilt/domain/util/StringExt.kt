package com.example.cleanarchitecturehilt.domain.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun String?.formattingDate(oldFormat: String?, newFormat: String): String {
    this?.let {
        val old = SimpleDateFormat(oldFormat)
        val new = SimpleDateFormat(newFormat)

        try {
            val oldDate = old.parse(this)
            oldDate?.let {
                return new.format(it)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e(javaClass.simpleName, "Ext formattingDate function Error!!! ${e.message}")
        }
    }
    return "-"
}


fun String?.formattingDecimal(): String =
    if (this?.trim()?.isBlank() == true) "0"
    else DecimalFormat("#,###").format(this?.trim()?.toInt())