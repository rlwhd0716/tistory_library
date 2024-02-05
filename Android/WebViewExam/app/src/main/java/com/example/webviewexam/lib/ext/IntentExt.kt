package com.example.webviewexam.lib.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri

@SuppressLint("QueryPermissionsNeeded")
fun Activity.callIntent(tel: String) {
    val mIntent = Intent(Intent.ACTION_DIAL)
    mIntent.data = Uri.parse("tel:$tel")

    if (mIntent.resolveActivity(packageManager) != null) {
        startActivity(mIntent)
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun Activity.smsIntent(tel: String, body: String) {
    val mIntent = Intent(Intent.ACTION_SEND)
    mIntent.data = Uri.parse("smsto:$tel")
    mIntent.putExtra("sms_body", body)

    if (mIntent.resolveActivity(packageManager) != null) {
        startActivity(mIntent)
    }
}


