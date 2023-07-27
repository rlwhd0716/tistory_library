package com.example.cleanarchitecturekoin.lib.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.cleanarchitecturekoin.lib.ext.hideSoftInputFromWindow
import com.example.cleanarchitecturekoin.lib.ext.toastShow
import org.koin.android.compat.SharedViewModelCompat
import com.example.cleanarchitecturekoin.BR

abstract class AbsFragment<B : ViewDataBinding, VM : AbsViewModel>(
    layoutResId: Int,
    viewModelCls: Class<VM>
) : Fragment(layoutResId) {
    protected var binding: B? = null
    protected val viewModel by SharedViewModelCompat.sharedViewModel(
        fragment = this,
        clazz = viewModelCls
    )
    protected open var bindingApply: (B.() -> Unit)? = null
    protected open var vmApply: (VM.() -> Unit)? = null
    protected open var onBackPressed: OnBackPressedCallback.() -> Unit = {}

    val TAG = javaClass.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(requireView())

        with(binding) {
            this?.lifecycleOwner = viewLifecycleOwner
            this?.setVariable(BR.vm, viewModel)
            this?.let { bindingApply?.invoke(it) }
        }

        with(viewModel) {
            lifecycle.addObserver(this)
            startActivity.observe(viewLifecycleOwner) {
                startActivity(Intent(activity, it.first))
                if (it.second) activity?.finish()
            }
            toast.observe(viewLifecycleOwner) { toastShow(it) }
            finish.observe(viewLifecycleOwner) { requireActivity().finish() }

            startActivityIntent.observe(viewLifecycleOwner) {
                startActivity(Intent(requireActivity(), it.second).apply(it.first))
                if (it.third) requireActivity().finish()
            }

            hideKeyboard.observe(viewLifecycleOwner) { hideSoftInputFromWindow() }
//            showAlertDialog.observe(viewLifecycleOwner) {
//                requireContext().showAlertDialog(
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
//            }
            vmApply?.invoke(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private var backPressedTime: Long = 0
    fun delayDestroy(block: () -> Unit = {}) {
        val tempTime = System.currentTimeMillis()
        when (tempTime - backPressedTime) {
            in 0..2000 -> block()
            else -> {
                backPressedTime = tempTime
                toastShow("getString(R.string.exit_message)", Toast.LENGTH_SHORT)
            }
        }
    }
}
