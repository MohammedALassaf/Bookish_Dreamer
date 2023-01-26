package com.example.booklet.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.booklet.*
import com.example.booklet.database.BookDB
import com.example.booklet.database.BookDC
import com.example.booklet.profile.ProfileActivity
import com.example.booklet.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritesActivity : AppCompatActivity() {
    private val db = BookDB(this)
    private lateinit var adapter: FavoritesAdapter
    private lateinit var list: RecyclerView
    private lateinit var viewModel: FavoritesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        viewModel.initDB(db)
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomnav.selectedItemId = R.id.favoritesmenu
        list = findViewById(R.id.favroot)
        val book = intent.getParcelableExtra("book") as? BookDC
        if (book != null) {
            val bookDC =
                BookDC(book.isbn, null, book.name, book.author, book.page, book.image, "YES", 1)
            db.insertBook(bookDC)
        }
        val wow = db.getBooks()
        listadapter(wow)

        bottomnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.searchmenu -> startActivity(Intent(this, SearchActivity::class.java))
                R.id.profilemenu -> startActivity(Intent(this, ProfileActivity::class.java))
                else -> Unit
            }
            true
        }


    }

    private fun listadapter(wow: List<BookDC>) {
        adapter = FavoritesAdapter(wow) {
            Log.d("TAG", "onCreate: Hello $it ")
            val index = it
            val numberDialog = NumberDialog { it1 ->
                if (viewModel.checkNumber(index + 1, it1.toLong())) {
                    viewModel.updatePageNum(index+1, it1.toLong())
                    adapter.updateFunction(it1, index)
                    Log.d("TAG", "onCreate: $it1 ")
                }else{
                    Toast.makeText(this, "Please enter a valid page number", Toast.LENGTH_SHORT).show()
                }

            }
            numberDialog.show(supportFragmentManager, NumberDialog::javaClass.name)
        }
        list.adapter = adapter
    }


}