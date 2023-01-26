package com.example.booklet.search

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.booklet.database.BookDC
import com.example.booklet.Dataclass.VolumeInfo
import com.example.booklet.R
import com.example.booklet.favorites.FavoritesActivity

class BookVH(view: View) : RecyclerView.ViewHolder(view) {

    val title = view.findViewById<TextView>(R.id.booktitle)
    val author = view.findViewById<TextView>(R.id.bookauthor)
    val number = view.findViewById<TextView>(R.id.booknumber)
    val image = view.findViewById<ImageView>(R.id.bookimage)
    private val root = view.findViewById<ConstraintLayout>(R.id.root)
    private lateinit var isbn: String

    fun bindData(volumeInfo: VolumeInfo) {
        title.text = volumeInfo.title
        author.text = volumeInfo.authors.joinToString(" ")
        number.text = volumeInfo.pageCount.toString()
        if (volumeInfo.imageLinks.thumbnail == null) {
            image.setImageResource(R.drawable.iconerror)
        } else {
            val imag = volumeInfo.imageLinks.thumbnail.toUri().buildUpon().scheme("https").build()
            image.load(imag) {
                this.placeholder(R.color.white)
                this.error(R.drawable.iconerror)
            }
        }
        for (i in volumeInfo.industryIdentifiers)
            if (i.type == "ISBN_13")
                isbn = i.identifier
        root.setOnClickListener {
            val context = root.context
            val builder = AlertDialog.Builder(context)
            val book = BookDC(
                isbn = isbn.toLong(),
                name = volumeInfo.title,
                author = volumeInfo.authors.joinToString(" "),
                page = volumeInfo.pageCount,
                image = volumeInfo.imageLinks.thumbnail,
                complete = "No",
                stop = 0
            )
            builder.setTitle("add to Favorites")
            builder.setMessage("Do you want to add the book to your favorites?")

            builder.setPositiveButton("Add") { dialog, which ->
                val intent = Intent(context, FavoritesActivity::class.java)
                intent.putExtra("book", book)
                context.startActivity(intent)
            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->

            }


            builder.show()

        }
    }


}

