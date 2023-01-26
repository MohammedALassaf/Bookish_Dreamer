package com.example.booklet.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booklet.database.BookDC
import com.example.booklet.R

class FavoritesAdapter(
    private val testing: List<BookDC>,
    private val onClickListener: (index: Int) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.favorites_item_layout, parent, false)
        return FavoritesVH(inflater)
    }

    override fun getItemCount(): Int = testing.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val test = testing[position]
        if (holder is FavoritesVH) holder.bindData(test, onClickListener, position)
    }

    fun updateFunction(page: String, index: Int) {
        this.testing[index].stop = page.toInt()
        notifyItemChanged(index)
    }

}