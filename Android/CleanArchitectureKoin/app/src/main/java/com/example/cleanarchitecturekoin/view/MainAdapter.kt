package com.example.cleanarchitecturekoin.view

import com.example.cleanarchitecturekoin.R
import com.example.cleanarchitecturekoin.databinding.ItemMainBinding
import com.example.cleanarchitecturekoin.lib.ui.AbsAdapter
import com.example.domain.model.TestData

class MainAdapter: AbsAdapter<TestData, ItemMainBinding>(R.layout.item_main) {


    interface OnItemClickListener {
        fun onItemClick()
    }
}