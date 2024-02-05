package com.example.webviewexam.view

import android.net.Uri
import android.os.Bundle
import com.example.webviewexam.R
import com.example.webviewexam.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.webviewexam.lib.ui.AbsActivity

@AndroidEntryPoint
class WebViewActivity : AbsActivity<ActivityWebViewBinding, WebViewViewModel>(R.layout.activity_web_view) {
    private var uri: Uri = Uri.EMPTY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}