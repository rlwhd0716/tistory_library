package com.example.appstatecheck

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

class Application: android.app.Application(), LifecycleObserver {

    companion object {
        var isBackground = true
    }

    override fun onCreate() {
        super.onCreate()

//        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onForeground() {
        Log.d(javaClass.name, "onForeground")
        isBackground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        Log.d(javaClass.name, "onBackground")
        isBackground = true
    }

}