package com.example.webviewexam.lib.util

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.webviewexam.R

object ProgressDialogUtil {
    private var progressDialog: Dialog? = null

    fun show(context: Context) {
//        if (progressDialog == null) {
//            progressDialog = Dialog(context).apply {
//                val layout = layoutInflater.inflate(R.layout.dialog_progress, null)
//                val text = layout.findViewById<TextView>(R.id.loading_text)
//                layout.findViewById<ImageView>(R.id.loading_progress).apply {
//
//                    val value = ValueAnimator.ofFloat(0.5f, 1.0f)
//                    value.duration = 2000
//                    value.repeatMode = ValueAnimator.REVERSE
//                    value.repeatCount = ValueAnimator.INFINITE
//                    value.addUpdateListener(AnimatorUpdateListener { animation ->
//                        val value = animation.animatedValue as Float
//                        this.alpha = value
//                        text.alpha = value
//                    })
//
//                    value.start()
//                }
//
//                text.apply {
//                    val valueDot = ValueAnimator.ofInt(1, 6)
//                    valueDot.duration = 2000
//                    valueDot.repeatCount = ValueAnimator.INFINITE
//                    valueDot.addUpdateListener(AnimatorUpdateListener { animation ->
//                        val value = animation.animatedValue
//                        var dot = ""
//
//                        if (value == 1) {
//                            this.text = "Loading"
//                        }
//
//                        when (value) {
//                            2 -> dot = "."
//                            3 -> dot = ".."
//                            4 -> dot = "..."
//                            5 -> dot = "...."
//                        }
//
//                        this.text = "Loading${dot}"
//                    })
//
//                    valueDot.start()
//                }
//                setContentView(layout)
//                setCancelable(false)
//                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            }
//            progressDialog?.show()
//        } else {
//            if (progressDialog?.isShowing == false) progressDialog?.show()
//        }
    }

    fun dismiss() {
        progressDialog?.dismiss()
    }

    fun destroy() {
        progressDialog?.let { if (it.isShowing) it.dismiss() }
        progressDialog = null
    }
}