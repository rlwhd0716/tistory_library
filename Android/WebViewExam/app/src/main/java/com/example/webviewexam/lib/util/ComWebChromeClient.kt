package com.example.webviewexam.lib.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import com.example.webviewexam.R
import timber.log.Timber


open class ComWebChromeClient(private val context: Context) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onCreateWindow(
        view: WebView,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message
    ): Boolean {
        Timber.e("window.open")
        val openWebView = WebView(view.context).apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
        }

        // 다이얼로그 생성되는 부분
        val dialog = Dialog(view.context, R.style.Theme_WebviewDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(openWebView)
            setOnKeyListener { d, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (openWebView.canGoBack()) {
                        openWebView.goBack()
                    } else {
                        Timber.e("Window.open 종료")
                        openWebView.visibility = View.GONE
                        openWebView.destroy()
                        d.dismiss()
                    }
                    return@setOnKeyListener true
                } else {
                    return@setOnKeyListener false
                }
            }
        }
        dialog.show()

        openWebView.webViewClient = ComWebViewClient(context)
        openWebView.webChromeClient = object : WebChromeClient() {
            override fun onCloseWindow(window: WebView) {
                Timber.e("onCloseWindow 1")
                window.visibility = View.GONE
                window.destroy()
                dialog.dismiss()
                super.onCloseWindow(window)
            }
        }

        (resultMsg.obj as WebViewTransport).webView = openWebView
        resultMsg.sendToTarget()
        return true
    }

    override fun onCloseWindow(window: WebView) {
        Timber.e("onCloseWindow 2")
        window.visibility = View.GONE
        window.destroy()
        super.onCloseWindow(window)
    }

    override fun onPermissionRequest(request: PermissionRequest?) {
        Timber.e("onPermissionRequest")
        try {
            request?.grant(request.resources)
        } catch (e: Exception) {
            Timber.e("permissionRequest: $e")
        }
    }



    override fun getDefaultVideoPoster(): Bitmap? {
        return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
    }
}