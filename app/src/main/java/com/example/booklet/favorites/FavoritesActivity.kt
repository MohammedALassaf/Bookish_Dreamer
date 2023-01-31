package com.example.booklet.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.booklet.*
import com.example.booklet.database.*
import com.example.booklet.profile.ProfileActivity
import com.example.booklet.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesActivity : AppCompatActivity() {
    private val db = BookDB(this)
    private lateinit var bookDao: BookDao
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var adapter: FavoritesAdapter
    private lateinit var list: RecyclerView
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var roomDB: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        initRoom()
        viewModel.initDB(db)
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomnav.selectedItemId = R.id.favoritesmenu
        list = findViewById(R.id.favroot)
        val book = intent.getParcelableExtra("book") as? BookDC
        if (book != null) {
            val bookDC =
                BookDC(book.isbn, null, book.name, book.author, book.page, book.image, "YES", 1)
            db.insertBook(bookDC)
            scope.launch {
                addBook(
                    Book(
                        0,
                        book.isbn,
                        book.name,
                        book.author,
                        book.page,
                        book.image,
                        1
                    )
                )

                getbook()
            }
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

    private suspend fun getbook() {
        val test = bookDao.getAllBooks()
        for (i in test)
            Log.d("TAG", "getbook: ${i.author} ")
    }

    private fun listadapter(wow: List<BookDC>) {
        adapter = FavoritesAdapter(wow) {
            Log.d("TAG", "onCreate: Hello $it ")
            val index = it
            val numberDialog = NumberDialog { it1 ->
                if (viewModel.checkNumber(index + 1, it1.toLong())) {
                    viewModel.updatePageNum(index + 1, it1.toLong())
                    adapter.updateFunction(it1, index)
                    Log.d("TAG", "onCreate: $it1 ")
                } else {
                    Toast.makeText(this, "Please enter a valid page number", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            numberDialog.show(supportFragmentManager, NumberDialog::javaClass.name)
        }
        list.adapter = adapter
    }

    private fun initRoom() {
        roomDB = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "book_database"
        ).build()
        bookDao = roomDB.BookDao()
    }

    private suspend fun addBook(book: Book) {
        bookDao.insertBook(book)
    }


}