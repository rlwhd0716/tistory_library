package com.example.webviewexam.lib.ext

import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

@BindingAdapter("isOpenDrawer")
fun isOpenDrawer(layout: DrawerLayout, isOpen: Boolean) {
    if (isOpen) {
        layout.openDrawer(GravityCompat.START)
    } else {
        layout.closeDrawer(GravityCompat.START)
    }
}