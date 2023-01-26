package com.example.booklet.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.booklet.database.BookDB
import com.example.booklet.R
import com.example.booklet.favorites.FavoritesActivity
import com.example.booklet.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private val db = BookDB(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomnav)
        val page = findViewById<TextView>(R.id.pagescount)
        val finish = findViewById<TextView>(R.id.booksfinished)
        val book = findViewById<TextView>(R.id.bookscount)
        val bookscount = db.getBooks()
        var pagescount = 0
        var booksFinished = 0
        for (i in bookscount) {
            pagescount += i.page
            if (i.page == i.stop)
                booksFinished++
        }
        finish.text = "Books Finished: $booksFinished"
        page.text = "Pages count: ${pagescount.toString()}"
        book.text = "Books count: ${bookscount.size}"
        bottomnav.selectedItemId = R.id.profilemenu
        bottomnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.searchmenu -> startActivity(Intent(this, SearchActivity::class.java))
                R.id.favoritesmenu -> startActivity(Intent(this, FavoritesActivity::class.java))
                else -> Unit
            }
            true
        }
    }
}