package com.example.webviewexam.lib.ext

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyChange() {
    val item = value
    logE("LiveData Item = $item")
    postValue(item)
}