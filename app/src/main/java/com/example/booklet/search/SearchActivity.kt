package com.example.booklet.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.booklet.Dataclass.Books
import com.example.booklet.Dataclass.VolumeInfo
import com.example.booklet.profile.ProfileActivity
import com.example.booklet.R
import com.example.booklet.favorites.FavoritesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var adapter: BookAdapter
    private lateinit var books: Books

    private lateinit var list: RecyclerView
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        val toolbar = findViewById<Toolbar>(R.id.searchtoolbar)
        val search = findViewById<EditText>(R.id.search)
        val btn = findViewById<Button>(R.id.button)
        list = findViewById(R.id.recyclerview)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomnav)


        bottomnav.selectedItemId = R.id.searchmenu
        search.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        bottomnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.favoritesmenu -> startActivity(Intent(this, FavoritesActivity::class.java))
                R.id.profilemenu -> startActivity(Intent(this, ProfileActivity::class.java))
                else -> Unit
            }
            true
        }

        btn.setOnClickListener {
            val volumeInfos = arrayListOf<VolumeInfo>()
            scope.launch {
                viewModel.getMyData(search.text.toString()).collect {
                    if (it != null) {
                        for (i in it.items) {
                            volumeInfos.add(i.volumeInfo)
                            listadapter(volumeInfos)
                        }
                        Log.d("TAG", "onCreate: ${it.totalItems}")
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("TAG", "onCreate: ")
                    }

                }
            }

        }


    }

    fun listadapter(volumeInfos: List<VolumeInfo>) {
        adapter = BookAdapter(volumeInfos)
        val count = adapter.itemCount
        list.adapter = adapter
        Log.d("Item Count", "$count")

    }

}