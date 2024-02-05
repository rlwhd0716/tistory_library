package com.example.webviewexam.lib.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

fun Fragment.replaceFragmentInFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun Fragment.addFragmentInFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.transact {
        add(frameId, fragment)
    }
}


inline fun <reified T : Fragment> T.startActivity(noinline unit: (() -> Unit)? = null) {
    startActivity(Intent(requireActivity(), T::class.java))
    unit?.invoke()
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}

fun Fragment.toastShow(msg: Any?, duration: Int = Toast.LENGTH_SHORT, unit: (() -> Unit)? = null) {
    if ((requireActivity()).isFinishing) return
    when (msg) {
        is String -> msg
        is Int -> getString(msg)
        else -> null
    }?.let {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            requireActivity().run { Toast.makeText(applicationContext, it, duration).show() }
        }
    }
    unit?.invoke()
}

fun Fragment.showKeyboard() {
    val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view?.rootView, 0)
}

fun Fragment.hideKeyboard() {
    val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}