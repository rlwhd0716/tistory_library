package com.leegi.lib_test

import android.content.Context
import android.widget.Toast

object TestObject {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}