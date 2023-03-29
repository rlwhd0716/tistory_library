package com.example.appstatecheck

import androidx.lifecycle.LifecycleObserver

class Application: android.app.Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
    }
}