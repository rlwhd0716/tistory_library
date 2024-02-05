package com.example.webviewexam.lib.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelLazy
import com.example.webviewexam.BR
import com.example.webviewexam.R
import com.example.webviewexam.lib.ext.hideKeyboard
import com.example.webviewexam.lib.ext.showAlertDialog
import com.example.webviewexam.lib.ext.showKeyboard
import com.example.webviewexam.lib.ext.toastShow
import com.example.webviewexam.lib.util.ProgressDialogUtil
import java.lang.reflect.ParameterizedType

abstract class AbsActivity<B : ViewDataBinding, VM : AbsViewModel>(layoutId: Int) :
    AppCompatActivity(layoutId) {
    protected val binding by lazy {
        DataBindingUtil
            .bind<B>(
                (window.decorView.findViewById(android.R.id.content) as ViewGroup)
                    .getChildAt(0)
            )
    }

    //    protected abstract val viewModel: VM
    protected val viewModel by ViewModelLazy(
        ((javaClass.genericSuperclass as ParameterizedType?)?.actualTypeArguments?.get(1) as Class<VM>).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

    private val BACKPRESS_INTERVAL = 2000

    protected open var bindingApply: (B.() -> Unit)? = null
    protected open var vmApply: (VM.() -> Unit)? = null
    protected open var isTwoTouchAppExit = false
    private var backKeyPressedTime : Long= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            this?.lifecycleOwner = this@AbsActivity
            this?.setVariable(BR.vm, viewModel)
            this?.let { bindingApply?.invoke(it) }
        }


        with(viewModel) viewModel@{
            lifecycle.addObserver(this@viewModel)
            toast.observe(this@AbsActivity) { toastShow(it) }
            finish.observe(this@AbsActivity) { finish() }
            finishAffinity.observe(this@AbsActivity) { finishAffinity() }
            backPressed.observe(this@AbsActivity) { onBackPressed() }
            startActivity.observe(this@AbsActivity) {
                startActivity(Intent(this@AbsActivity, it.first))
                if (it.second) finish()
            }
            startActivityIntent.observe(this@AbsActivity) {
                startActivity(Intent(this@AbsActivity, it.second).apply(it.first))
                if (it.third) finish()
            }
            isLoading.observe(this@AbsActivity) {
                if (it) ProgressDialogUtil.show(this@AbsActivity)
                else ProgressDialogUtil.dismiss()
            }
            isKeyboard.observe(this@AbsActivity) {
                if (it) {
                    showKeyboard()
                } else {
                    hideKeyboard()
                }
            }
            showAlertDialog.observe(this@AbsActivity) {
                showAlertDialog(
                    title = it.first.first,
                    message = it.first.second,
                    layout = true,
                    ok = if (it.second != null) it.second?.let { o -> o.first } else null,
                    okAction = if (it.second != null) {
                        it.second?.second
                    } else null,
                    cancel = if (it.third != null) it.third?.let { c -> c.first } else null,
                    cancelAction = if (it.third != null) {
                        it.third?.second
                    } else null
                )
            }

            vmApply?.invoke(this)
            onBackPressedDispatcher.addCallback(this@AbsActivity, onAbsBackPressedCallback)
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        ProgressDialogUtil.destroy()
        onAbsBackPressedCallback.remove()
        super.onDestroy()
    }

    fun removeBackPressedCallback(){
        onAbsBackPressedCallback.remove()
    }

    private val onAbsBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (isTwoTouchAppExit) {

                if(System.currentTimeMillis() > backKeyPressedTime + BACKPRESS_INTERVAL){
                    backKeyPressedTime = System.currentTimeMillis()
//                    toastShow(R.string.text_app_exit_warning_explantion)
                }else{
                    finish()
                }
            } else {
                finish()
            }
        }
    }
}

