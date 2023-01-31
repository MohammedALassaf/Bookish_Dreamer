package com.example.booklet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    val isbn: Long,

    @ColumnInfo(name = "bookName")
    val name: String,

    val author: String,
    @ColumnInfo(name = "numberOfPages")
    val page: Int,

    @ColumnInfo(name = "imageURL")
    val image: String,

    @ColumnInfo(name = "pageStopped")
    var stop: Int

)