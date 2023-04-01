package com.example.appstatecheck

import android.util.Log
import androidx.lifecycle.*

class Application: android.app.Application(), LifecycleObserver {

    companion object {
        var isBackground = true
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
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

/*
class Application: android.app.Application(), LifecycleEventObserver {

    companion object {
        var isBackground = true
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_RESUME -> {
                Log.d(javaClass.name, "onForeground")
                isBackground = false
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d(javaClass.name, "onBackground")
                isBackground = true
            }
            else -> {}
        }
    }
}*/
