package com.example.cleanarchitecturekoin.lib.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.cleanarchitecturekoin.lib.ext.toastShow
import org.koin.android.compat.ViewModelCompat
import java.lang.reflect.ParameterizedType
import com.example.cleanarchitecturekoin.BR

abstract class AbsActivity<B : ViewDataBinding, VM : AbsViewModel>(layoutId: Int) :
    AppCompatActivity(layoutId) {
    protected val binding by lazy {
        DataBindingUtil
            .bind<B>(
                (window.decorView.findViewById(android.R.id.content) as ViewGroup)
                    .getChildAt(0)
            )
    }

    @Suppress("UNCHECKED_CAST")
    protected val viewModel by ViewModelCompat.viewModel(
        owner = lazyOf(this),
        clazz = ((javaClass.genericSuperclass as ParameterizedType?)
            ?.actualTypeArguments
            ?.get(1) as Class<VM>)
    )

    protected open var bindingApply: (B.() -> Unit)? = null
    protected open var vmApply: (VM.() -> Unit)? = null

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
            hideKeyboard.observe(this@AbsActivity) {
//                hideKeyboard()
            }

            showAlertDialog.observe(this@AbsActivity) {
//                showAlertDialog(
//                    title = getString(it.first.first),
//                    message = getString(it.first.second),
//                    ok = getString(it.first.third),
//                    cancel = getString(R.string.dialog_cancel),
//                    layout = it.second,
//                    okAction = {
//                        it.third.invoke()
//                    },
//                    positiveListener = { d, _ ->
//                        it.third.invoke()
//                        d.dismiss()
//                    },
//                    negativeListener = { d, _ ->
//                        d.cancel()
//                    }
//                )
            }
            vmApply?.invoke(this)
        }

    }
}

object ProgressDialogUtil {
    private var progressDialog: Dialog? = null

    fun show(context: Context) {
        if (progressDialog == null) {
//            progressDialog = Dialog(context).apply {
//                val layout = layoutInflater.inflate(R.layout.dialog_progress, null)
//                layout.findViewById<ImageView>(R.id.loading_progress).apply {
//                    val rotate = RotateAnimation(
//                        0f, 360f,
//                        Animation.RELATIVE_TO_SELF, 0.5f,
//                        Animation.RELATIVE_TO_SELF, 0.5f
//                    )
//                    rotate.duration = 1000
//                    rotate.repeatCount = Animation.INFINITE
//                    rotate.interpolator = LinearInterpolator()
//                    startAnimation(rotate)
//                }
//                setContentView(layout)
//                setCancelable(false)
//                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            }
            progressDialog?.show()
        }
    }

    fun dismiss() {
        progressDialog?.let { if (it.isShowing) it.dismiss() }
        progressDialog = null
    }
}