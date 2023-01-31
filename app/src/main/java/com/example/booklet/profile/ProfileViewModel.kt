package com.example.booklet.profile

import androidx.lifecycle.ViewModel
import com.example.booklet.database.BookDB

class ProfileViewModel: ViewModel() {

    private lateinit var db: BookDB
    fun initDB(database: BookDB){
        db = database
    }

    fun booksFinished(): Int{
        val books = db.getBooks()
        var bookfinished = 0
        for (book in books){
           if (book.page == book.stop)
               bookfinished++
        }
        return bookfinished
    }

    fun getAll():Int = db.getBooks().size

    fun getPages(): Int{
        val books = db.getBooks()
        var pages = 0
        for (book in books){
            pages += book.stop
        }
        return pages
    }
}