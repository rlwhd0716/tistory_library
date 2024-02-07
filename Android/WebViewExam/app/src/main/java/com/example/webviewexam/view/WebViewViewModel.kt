package com.example.webviewexam.view

import android.webkit.JavascriptInterface
import androidx.lifecycle.LiveData
import com.example.webviewexam.lib.ui.AbsViewModel
import com.example.webviewexam.lib.util.ComWebChromeClient
import com.example.webviewexam.lib.util.ComWebViewClient
import com.example.webviewexam.lib.util.JavaScriptBridge
import com.example.webviewexam.lib.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(): AbsViewModel() {
    var client: ComWebViewClient? = null
    var chromeClient: ComWebChromeClient? = null
    var javaScriptBridge: JavaScriptBridge? = null
    var mainUrl = ""


    private val _callDeviceInfoLiveData = SingleLiveEvent<Boolean>()
    val callDeviceInfo: LiveData<Boolean> get() = _callDeviceInfoLiveData

    @JavascriptInterface
    fun callDeviceInfo() {
        _callDeviceInfoLiveData.postValue(true)
    }
}