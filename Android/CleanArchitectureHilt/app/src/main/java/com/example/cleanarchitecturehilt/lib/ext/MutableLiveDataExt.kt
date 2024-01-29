package com.example.cleanarchitecturehilt.lib.ext

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyChange() {
    val item = value
    logE("LiveData Item = $item")
    postValue(item)
}