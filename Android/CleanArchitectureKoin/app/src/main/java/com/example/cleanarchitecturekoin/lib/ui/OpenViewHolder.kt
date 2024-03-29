package com.example.cleanarchitecturekoin.lib.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class OpenViewHolder<T, B : ViewDataBinding>(parent: ViewGroup?, @LayoutRes layoutRes: Int) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false)
    ) {
    val binding: B = DataBindingUtil.bind(itemView)!!

    open fun bind(item: T) {}
}
