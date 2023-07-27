package com.example.cleanarchitecturekoin

import com.example.cleanarchitecturekoin.di.appModules
import com.example.domain.util.pref.Pref
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application: android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModules)
        }
        Pref.initializeApp(this)
//        ResourceProvider.initializeApp(this)
//        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}