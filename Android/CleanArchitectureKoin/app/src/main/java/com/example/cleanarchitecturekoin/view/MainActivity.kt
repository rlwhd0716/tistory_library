package com.example.cleanarchitecturekoin.view

import android.os.Bundle
import com.example.cleanarchitecturekoin.R
import com.example.cleanarchitecturekoin.databinding.ActivityMainBinding
import com.example.cleanarchitecturekoin.lib.ui.AbsActivity

class MainActivity : AbsActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override var vmApply: (MainViewModel.() -> Unit)? = {

    }

    override var bindingApply: (ActivityMainBinding.() -> Unit)? = {

    }
}