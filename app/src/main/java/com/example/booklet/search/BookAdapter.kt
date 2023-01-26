package com.example.booklet.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booklet.Dataclass.VolumeInfo
import com.example.booklet.R

class BookAdapter(
    private val testing: List<VolumeInfo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.book_item_layout, parent, false)
        return BookVH(inflater)
    }

    override fun getItemCount(): Int = testing.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val test = testing[position]
        if (holder is BookVH) holder.bindData(test)
    }


}