package com.example.webviewexam.lib.util

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity
import com.example.webviewexam.lib.ext.setLocalStorage
import timber.log.Timber
import java.net.URISyntaxException


class ComWebViewClient(
    private val context: Context,
    private val localStorageData: List<Pair<String, String>> = listOf(),
    private val webViewLoadingInterface: WebViewLoadingInterface? = null
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        Timber.e("onPageStarted : $url")
        super.onPageStarted(view, url, favicon)
        view?.setNetworkAvailable(true)
        localStorageData.forEach {
            view?.setLocalStorage(it.first, it.second)
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        Timber.e("onPageFinished : $url")
        super.onPageFinished(view, url)
        webViewLoadingInterface?.onPageFinished(url)
    }

    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)

        Timber.e("ssl Error - $error")
        handler?.proceed()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Timber.e("requestUrl - ${request?.url}")
        if (!request?.url.toString().startsWith("http://") && !request?.url.toString()
                .startsWith("https://") && !request?.url.toString().startsWith("javascript:")
        ) {
            var intent: Intent? = null
            return try {
                intent = Intent.parseUri(request?.url.toString(), Intent.URI_INTENT_SCHEME)
                val uri: Uri = Uri.parse(intent.dataString)
                startActivity(context, Intent(Intent.ACTION_VIEW, uri), null)
                true
            } catch (ex: URISyntaxException) {
                false
            } catch (e: ActivityNotFoundException) {
                if (intent == null) return false
                val packageName = intent.getPackage()
                if (packageName != null) {
                    startActivity(
                        context,
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        ),
                        null
                    )
                    return true
                }
                false
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }
}

interface WebViewLoadingInterface {
    fun onPageFinished(url:String?)
}