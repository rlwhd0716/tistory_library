package com.example.cleanarchitecturekoin.view

import android.os.Bundle
import com.example.cleanarchitecturekoin.R
import com.example.cleanarchitecturekoin.databinding.FragmentMainBinding
import com.example.cleanarchitecturekoin.lib.ui.AbsFragment

class MainFragment : AbsFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main,
    MainViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override var bindingApply: (FragmentMainBinding.() -> Unit)? = {

    }

    override var vmApply: (MainViewModel.() -> Unit)? = {

    }
}