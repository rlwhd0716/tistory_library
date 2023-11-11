package com.example.recyclerviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexample.databinding.ItemRecyclerviewBinding

class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {
    var datas: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recyclerview,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}

open class MyViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val binding: ItemRecyclerviewBinding? = DataBindingUtil.bind(item)

    fun bind(data: String) {
        binding?.itemTextView?.text = data
    }
}