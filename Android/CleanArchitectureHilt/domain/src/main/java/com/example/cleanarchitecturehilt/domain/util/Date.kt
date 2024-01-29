package com.example.cleanarchitecturehilt.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentTime() = SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date(System.currentTimeMillis()))
fun getCurrentDate() = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date(System.currentTimeMillis()))
fun getCurrentYearMonth() = SimpleDateFormat("yyyyMM", Locale.KOREA).format(Date(System.currentTimeMillis()))