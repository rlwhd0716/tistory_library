package com.example.cleanarchitecturehilt.lib.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cleanarchitecturehilt.lib.ext.logE


private var phoneNumber = ""

fun getMyPhoneNumber(): String {
    logE("myPhoneNumber - $phoneNumber")
    return phoneNumber
}


fun AppCompatActivity.setMyPhoneNumber() {
    if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
        val telephony = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            if (telephony.line1Number != null) {
                phoneNumber = telephony.line1Number
            } else {
                if (telephony.simSerialNumber != null) {
                    phoneNumber = telephony.simSerialNumber
                }
            }
        } catch (e: Exception) {
            logE("setMyPhoneNumber - $e")
        }

        if (phoneNumber.startsWith("+82")) {
            phoneNumber = phoneNumber.replace("+82", "0")
        }
    }
}
