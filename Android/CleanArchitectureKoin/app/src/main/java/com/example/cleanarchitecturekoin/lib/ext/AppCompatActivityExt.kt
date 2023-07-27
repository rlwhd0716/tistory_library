package com.example.cleanarchitecturekoin.lib.ext

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int, action: FragmentTransaction.() -> Unit = {}) {
    supportFragmentManager.transact {
        this.action()
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.addFragmentInActivity(fragment: Fragment, frameId: Int, action: FragmentTransaction.() -> Unit = {}) {
    supportFragmentManager.transact {
        this.action()
        add(frameId, fragment)
    }
}

fun AppCompatActivity.toastShow(msg: Any?, duration: Int = Toast.LENGTH_SHORT, action: (() -> Unit)? = null) {
    if (isFinishing) return
    when (msg) {
        is String -> msg
        is Int -> getString(msg)
        else -> null
    }?.let {
        lifecycleScope.launchWhenCreated {
            Toast.makeText(applicationContext, it, duration).show()
        }
    }
    action?.invoke()
}

inline fun <reified T : Activity> AppCompatActivity.startActivity(noinline block: (Intent.() -> Unit)? = null, noinline action: (() -> Unit)? = null) {
    startActivity(Intent(this, T::class.java).apply {
        block?.let { this.apply(block) }
    })
    action?.invoke()
}