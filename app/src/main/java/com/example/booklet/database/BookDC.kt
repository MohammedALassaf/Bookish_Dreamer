package com.example.booklet.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookDC(
    val isbn: Long,
    val id: Int? =null,
    val name: String,
    val author :String,
    val page: Int,
    val image: String,
    var complete: String,
    var stop: Int
): Parcelable