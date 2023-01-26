package com.example.booklet.favorites

import androidx.lifecycle.ViewModel
import com.example.booklet.database.BookDB
import com.example.booklet.database.BookDC

class FavoritesViewModel : ViewModel() {
    private lateinit var db: BookDB
    private lateinit var users: List<BookDC>

    fun initDB(database: BookDB) {
        db = database
    }

    fun checkNumber(id: Int, page: Long): Boolean {
        for (i in db.getBooks()) {
            if (i.id == id) {
                return i.page >= page && i.stop <=page
            }
        }
        return false
    }

    fun updatePageNum(id: Int, page: Long) {
        db.updatePage(id.toLong(), page)
    }
    fun removeBook(id:Int){
        db.removeBook(id)
    }

}