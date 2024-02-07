package com.example.webviewexam.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.addCallback
import com.example.webviewexam.BuildConfig
import com.example.webviewexam.R
import com.example.webviewexam.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.webviewexam.lib.ui.AbsActivity
import com.example.webviewexam.lib.util.ComWebChromeClient
import com.example.webviewexam.lib.util.ComWebViewClient
import com.example.webviewexam.lib.util.JavaScriptBridge
import com.example.webviewexam.lib.util.JavaScriptResponse
import com.example.webviewexam.lib.util.WebViewLoadingInterface
import timber.log.Timber

@AndroidEntryPoint
class WebViewActivity : AbsActivity<ActivityWebViewBinding, WebViewViewModel>(R.layout.activity_web_view),
    WebViewLoadingInterface, JavaScriptResponse {
    private var uri: Uri = Uri.EMPTY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override var bindingApply: (ActivityWebViewBinding.() -> Unit)? = {
        onBackPressedDispatcher.addCallback(this@WebViewActivity) {
            Timber.i("onBackPressed")
        }
    }

    override var vmApply: (WebViewViewModel.() -> Unit)? = {
        callDeviceInfo.observe(this@WebViewActivity) {
            // 단말기 정보 수집 기능
        }

        mainUrl = BuildConfig.WEBVIEW_URL
        client = ComWebViewClient(
            context = this@WebViewActivity,
            webViewLoadingInterface = this@WebViewActivity
        )
        chromeClient = ComWebChromeClient(this@WebViewActivity)
        javaScriptBridge = JavaScriptBridge(this@WebViewActivity)
    }

    override fun onPageFinished(url: String?) {}

    override fun setEventBack() {}
}