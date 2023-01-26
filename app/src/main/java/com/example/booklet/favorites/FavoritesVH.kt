package com.example.booklet.favorites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.booklet.database.BookDC
import com.example.booklet.R

class FavoritesVH(view: View) : RecyclerView.ViewHolder(view) {

    val title = view.findViewById<TextView>(R.id.favbookname)
    val author = view.findViewById<TextView>(R.id.favbookauthor)
    val number = view.findViewById<TextView>(R.id.favbookpage)
    val stop = view.findViewById<TextView>(R.id.favbookstop)
//    val complete = view.findViewById<TextView>(R.id.favbookcomplete)
    val image = view.findViewById<ImageView>(R.id.favbookimage)

    private val root = view.findViewById<ConstraintLayout>(R.id.recyclerroot)


    fun bindData(bookDC: BookDC, onClickListener: (index: Int) -> Unit, position: Int) {
        val img = bookDC.image.toUri().buildUpon().scheme("https").build()
        image.load(img) {
            this.placeholder(R.color.white)
            this.error(R.drawable.iconerror)
        }
        title.text = bookDC.name
        author.text = bookDC.author

//        complete.text = bookDC.complete
        stop.text = bookDC.stop.toString()

        if (bookDC.page == bookDC.stop){
            number.text = "Done"
            number.setTextColor(R.color.green)
        }else number.text = bookDC.page.toString()
        root.setOnClickListener {
            onClickListener(position)
        }
    }
}