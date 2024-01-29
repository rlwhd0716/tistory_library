package com.example.cleanarchitecturehilt.lib.ext

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.R
import androidx.appcompat.app.AlertDialog

fun Context.showAlertDialog(
    @StyleRes themeResId: Int = TypedValue().let {
        theme.resolveAttribute(R.attr.alertDialogTheme, it, true)
        it.resourceId
    },
    title: String = "",
    message: String,
    messageView: TextView.() -> Unit = {},
    layout: Boolean? = null,
    ok: String? = null,
    cancel: String? = "닫기",
    okAction: (() -> Unit)? = null,
    cancelAction: (() -> Unit)? = null,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null,
    negativeListener: ((DialogInterface, Int) -> Unit)? = null
) {
    Handler(mainLooper).post {
//        if (layout == true) {
//            val view =
//                LayoutInflater.from(this).inflate(com.example.cleanarchitecturehilt.R.layout.dialog_common, null)
//            val binding: DialogCommonBinding? = DataBindingUtil.bind(view)
//            val alert = AlertDialog.Builder(this@showAlertDialog).apply {
//                setCancelable(false)
//                setView(view)
//            }.create()
//            // 타이틀
//            binding?.setVariable(BR.title, title)
//            // 메시지
//            binding?.setVariable(BR.message, message)
//
//            // OK 버튼 유무 확인 후 설정
//            binding?.setVariable(BR.ok, ok)
//            binding?.setVariable(BR.positive, View.OnClickListener {
//                okAction?.invoke()
//                alert.dismiss()
//            })
//
//            // cancel 버튼 설정
//            binding?.setVariable(BR.cancel, cancel)
//            binding?.setVariable(BR.negative, View.OnClickListener {
//                cancelAction?.invoke()
//                alert.dismiss()
//            })
//
//            // alert 띄우기
//            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            alert.show()
//        } else {
            AlertDialog.Builder(this@showAlertDialog, themeResId).apply {
                if (title.isNotEmpty()) setTitle(title)
                if (message.isNotEmpty()) setMessage(message)
                setCancelable(false)
                ok?.let {
                    setPositiveButton(ok) { dialog, which ->
                        positiveListener?.invoke(dialog, which)
                        dialog.dismiss()
                    }
                }
                cancel?.let {
                    setNegativeButton(cancel) { dialog, which ->
                        negativeListener?.invoke(dialog, which)
                        dialog.dismiss()
                    }
                }
            }.show().apply {
                getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                (findViewById<TextView>(android.R.id.message))?.apply {
                    movementMethod = ScrollingMovementMethod()
                }?.apply(messageView)
            }
//        }
    }
}


fun Context.showAlertDialog(
    @StyleRes themeResId: Int = TypedValue().let {
        theme.resolveAttribute(R.attr.alertDialogTheme, it, true)
        it.resourceId
    },
    @StringRes title: Int = 0,
    @StringRes message: Int = 0,
    messageView: TextView.() -> Unit = {},
    @StringRes ok: Int = 0,
    @StringRes cancel: Int = 0,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null,
    negativeListener: ((DialogInterface, Int) -> Unit)? = null,
) {
    val t = if (title != 0) getString(title) else ""
    val m = if (message != 0) getString(message) else ""
    val o = if (ok != 0) getString(ok) else ""
    val c = if (cancel != 0) getString(cancel) else ""
    showAlertDialog(
        themeResId = themeResId,
        title = t,
        message = m,
        messageView = messageView,
        ok = o,
        cancel = c,
        positiveListener = positiveListener,
        negativeListener = negativeListener
    )
}

fun Context.showCustomDialog(
    @StyleRes themeResId: Int = TypedValue().let {
        theme.resolveAttribute(R.attr.alertDialogTheme, it, true)
        it.resourceId
    },
    @StringRes title: Int = 0,
    @StringRes message: Int = 0,
    @StringRes ok: Int = 0,
    @StringRes cancel: Int = 0,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null,
    negativeListener: ((DialogInterface, Int) -> Unit)? = null,
) {
    val t = if (title != 0) getString(title) else ""
    val m = if (message != 0) getString(message) else ""
    val o = if (ok != 0) getString(ok) else ""
    val c = if (cancel != 0) getString(cancel) else ""
    showAlertDialog(
        themeResId = themeResId,
        title = t,
        message = m,
        ok = o,
        cancel = c,
        positiveListener = positiveListener,
        negativeListener = negativeListener
    )
}

fun Context.showAlertDialogApply(block: AlertDialog.Builder.() -> Unit) {
    AlertDialog.Builder(this).apply(block).show()
}

//fun Context.showAlertEditDialog(
//    title: String = "",
//    contents: String,
//    ok: String = "수정",
//    cancel: String = "취소",
//    okAction: ((String?) -> Unit)? = null,
//    cancelAction: (() -> Unit)? = null,
//    maxCount: Int = 0
//) {
//    val view = LayoutInflater.from(this).inflate(com.example.cleanarchitecturehilt.R.layout.dialog_input, null)
//    val binding: DialogInputBinding? = DataBindingUtil.bind(view)
//    val alert = AlertDialog.Builder(this@showAlertEditDialog).apply {
//        setCancelable(false)
//        setView(view)
//    }.create()
//    // 타이틀
//    binding?.setVariable(BR.title, title)
//
//    if (maxCount > 0) {
//        binding?.etDialogEdit?.addTextChangedListener(
//            setOnTextWatcher(maxCount = maxCount) {
//                binding.tvMemoCounter.text = it
//            }
//        )
//    }
//    binding?.etDialogEdit?.setText(contents)
//    binding?.etDialogEdit?.setOnFocusChangeListener { _, _ ->
//        view.postDelayed({
//            run {
//                val imm =
//                    this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.showSoftInput(binding.etDialogEdit, InputMethodManager.SHOW_IMPLICIT)
//            }
//        }, 100L)
//    }
//    binding?.etDialogEdit?.requestFocus()
//
//    // OK 버튼 유무 확인 후 설정
//    if (okAction != null) {
//        binding?.setVariable(BR.isOk, View.VISIBLE)
//        binding?.setVariable(BR.ok, ok)
//        binding?.setVariable(BR.positive, View.OnClickListener {
//            okAction.invoke(binding.etDialogEdit.text.toString())
//            alert.dismiss()
//        })
//    } else {
//        binding?.setVariable(BR.isOk, View.GONE)
//    }
//    // cancel 버튼 설정
//    binding?.setVariable(BR.cancel, cancel)
//    binding?.setVariable(BR.negative, View.OnClickListener {
//        cancelAction?.invoke()
//        alert.dismiss()
//    })
//    // alert 띄우기
//    alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    alert.show()
//}