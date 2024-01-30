package com.example.cleanarchitecturehilt.lib.ext

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["onClick"])
fun View.onClickListener(listener: ((View) -> Any?)? = null) {
    setOnClickListener { listener?.invoke(it) }
}