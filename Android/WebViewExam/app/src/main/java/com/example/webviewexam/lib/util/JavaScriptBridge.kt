package com.example.webviewexam.lib.util

import android.webkit.JavascriptInterface

interface JavaScriptResponse {
    fun setEventBack()
}

class JavaScriptBridge(
    val loginResponse: JavaScriptResponse? = null
) {

    @JavascriptInterface
    fun setEventBack() {
        loginResponse?.setEventBack()
    }

}