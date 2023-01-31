package com.example.booklet.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
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
        val viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomnav)
        val page = findViewById<TextView>(R.id.pagescount)
        val finish = findViewById<TextView>(R.id.booksfinished)
        val book = findViewById<TextView>(R.id.bookscount)
        viewModel.initDB(db)

        finish.text = "Books Finished: ${viewModel.booksFinished()}"
        page.text = "Pages count: ${viewModel.getPages()}"
        book.text = "Books count: ${viewModel.getAll()}"


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