package com.example.booklet.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BookDB(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val initTable = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "$COLUMN_ISBN INTEGER," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_PAGE INTEGER," +
                "$COLUMN_IMAGE TEXT," +
                "$COLUMN_COMPLETE TEXT," +
                "$COLUMN_STOP INTEGER)"

        db.execSQL(initTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"

        db.execSQL(dropTable)
        onCreate(db)
    }

    fun insertBook(book: BookDC) {
        val db = writableDatabase
        val values = ContentValues()
        if (!checkBook(book.isbn)) {
            values.put(COLUMN_ISBN, book.isbn)
            values.put(COLUMN_NAME, book.name)
            values.put(COLUMN_AUTHOR, book.author)
            values.put(COLUMN_PAGE, book.page)
            values.put(COLUMN_IMAGE, book.image)
            values.put(COLUMN_COMPLETE, book.complete)
            values.put(COLUMN_STOP, book.stop)
            db.insert(TABLE_NAME, null, values)
        }

    }


    fun checkBook(isbn: Long): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ISBN = $isbn"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        if (cursor.isFirst) {
            return isbn == cursor.getLong(cursor.getColumnIndex(COLUMN_ISBN))
        }
        return false
    }

    fun updatePage(id: Long, page: Long) {
        val db = writableDatabase
        val values = ContentValues()
        val where = "$COLUMN_ID = $id"
        values.put(COLUMN_STOP , page)

        db.update(TABLE_NAME,values,where , null)
    }


    fun getBooks(): List<BookDC> {
        val db = readableDatabase
        val books = arrayListOf<BookDC>()
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        try {
            cursor.moveToFirst()
            if (cursor.isFirst)
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    val isbn = cursor.getLong(cursor.getColumnIndex(COLUMN_ISBN))
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    val author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))
                    val page = cursor.getInt(cursor.getColumnIndex(COLUMN_PAGE))
                    val image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                    val complete = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETE))
                    val stop = cursor.getInt(cursor.getColumnIndex(COLUMN_STOP))
                    val bookinfo = BookDC(isbn,id, name, author, page, image, complete, stop)
                    books.add(bookinfo)
                } while (cursor.moveToNext())

        } catch (exception: SQLiteException) {
            Log.d("exception", "getUser: ${exception.message}")
        }
        return books
    }
    fun removeBook(id: Int) {
        val db = writableDatabase
        val where = "$COLUMN_ID = $id"

        db.delete(TABLE_NAME, where, null)
    }

    companion object {
        // DB info
        const val DATABASE_NAME = "books_database"
        const val DATABASE_VERSION = 1

        //Table info
        const val TABLE_NAME = "book_table"
        const val COLUMN_ISBN = "isbn"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_PAGE = "page"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_COMPLETE = "complete"
        const val COLUMN_STOP = "stop"

    }


}