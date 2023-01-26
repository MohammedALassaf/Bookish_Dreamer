package com.example.booklet.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.booklet.R
import com.example.booklet.search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val username = findViewById<EditText>(R.id.username)
        val login = findViewById<Button>(R.id.login)
        val text = findViewById<TextView>(R.id.textView)

        if (!loadData().isNullOrBlank()) scope.launch {
            username.visibility = View.INVISIBLE
            login.visibility = View.INVISIBLE
            text.text = "Welcome Back ${loadData()}"
            text.textSize = 24f
            toolbar.visibility = View.INVISIBLE
            delay(1500)
            startActivity(Intent(applicationContext , SearchActivity::class.java))
        }

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        username.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        login.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            saveData(username)
            startActivity(Intent(applicationContext, SearchActivity::class.java))
        }

    }
    private fun saveData(text: EditText){
        val insertedText = text.text.toString()
        val sharedPreferences = getSharedPreferences("sharedPrefs" , Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("STRING_KEY", insertedText)
        }.apply()
        Toast.makeText(this, "Name saved", Toast.LENGTH_SHORT).show()
    }
     fun loadData(): String?{
        val sharedPreferences = getSharedPreferences("sharedPrefs" , Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY", null)
        return savedString
    }
}