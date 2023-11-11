package com.example.cleanarchitecturekoin.lib.ui

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class AbsAdapter<T, B: ViewDataBinding>(
    private val layoutId: Int
) : RecyclerView.Adapter<OpenViewHolder<T, B>>() {
    var items = mutableListOf<T>()
        set(value) {
            items.clear()
            items.addAll(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OpenViewHolder<T, B>(parent, layoutId)
    override fun onBindViewHolder(holder: OpenViewHolder<T, B>, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemId(position: Int): Long = super.getItemId(position)
    override fun getItemCount(): Int = items.size

    open var listener: OnItemClickListener? = null

    fun setOnClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, pos: Int)
    }

}