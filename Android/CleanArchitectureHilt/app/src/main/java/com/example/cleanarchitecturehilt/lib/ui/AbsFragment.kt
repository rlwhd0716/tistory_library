package com.example.cleanarchitecturehilt.lib.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitecturehilt.R
import com.example.cleanarchitecturehilt.lib.ext.hideKeyboard
import com.example.cleanarchitecturehilt.lib.ext.showKeyboard
import com.example.cleanarchitecturehilt.lib.ext.toastShow
import com.example.cleanarchitecturehilt.lib.util.ProgressDialogUtil
import java.lang.reflect.ParameterizedType

abstract class AbsFragment<B : ViewDataBinding, VM : AbsViewModel>(layoutResId: Int) :
    Fragment(layoutResId) {
    protected var binding: B? = null

    protected val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[
            ((javaClass.genericSuperclass as ParameterizedType?)?.actualTypeArguments?.get(1) as Class<VM>)
        ]
    }

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
            isLoading.observe(viewLifecycleOwner) {
                if (it) ProgressDialogUtil.show(requireContext())
                else ProgressDialogUtil.dismiss()
            }
            startActivityIntent.observe(viewLifecycleOwner) {
                startActivity(Intent(requireActivity(), it.second).apply(it.first))
                if (it.third) requireActivity().finish()
            }
            isKeyboard.observe(viewLifecycleOwner) {
                if (it) {
                    showKeyboard()
                } else {
                    hideKeyboard()
                }
            }
            vmApply?.invoke(this)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
//                toastShow(getString(R.string.exit_message))
            }
        }
    }
}
