package com.example.webviewexam

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Push, Provider, etc... register

    }

    fun getAppVersionName() : String? {
        try {
            return getLocalVersionName()
        } catch (e : PackageManager.NameNotFoundException) {
            Timber.e("앱 버전 가져오기 오류")
        }
        return null
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun getLocalVersionName() : String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).versionName
        } else {
            packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).versionName
        }
    }
}