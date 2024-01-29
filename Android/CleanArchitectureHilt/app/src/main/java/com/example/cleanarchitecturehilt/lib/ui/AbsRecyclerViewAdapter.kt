package com.example.cleanarchitecturehilt.lib.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class AbsRecyclerViewAdapter<B : ViewDataBinding, DATA>(val layoutId: Int) :
    RecyclerView.Adapter<RecyclerViewHolder<B, DATA>>() {

    open var datas = mutableListOf<DATA>()
        set(value) {
            datas.clear()
            datas.addAll(value)
        }

    private var onClickEvent: OnClickEvent<DATA>? = null

    fun getOnClickEvent(): OnClickEvent<DATA>? = onClickEvent

    @JvmName("RecyclerItemClickListener")
    fun setOnClickEvent(listener: OnClickEvent<DATA>) {
        onClickEvent = listener
    }

    abstract fun onBindItem(binding: B?, data: DATA, position: Int)

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerViewHolder<B, DATA>(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerViewHolder<B, DATA>, position: Int) {
        holder.bind(datas[position]) { binding, data, layoutPosition ->
            onBindItem(binding, data, layoutPosition)
        }
    }

    interface OnClickEvent<T> {
        fun itemClick(position: Int, item: T)
    }
}

open class RecyclerViewHolder<B : ViewDataBinding?, DATA>(val item: View) :
    RecyclerView.ViewHolder(item) {
    private val binding: B? = DataBindingUtil.bind(item)

    fun bind(data: DATA, block: (B?, DATA, Int) -> Unit) {
        block.invoke(binding, data, layoutPosition)
    }
}
