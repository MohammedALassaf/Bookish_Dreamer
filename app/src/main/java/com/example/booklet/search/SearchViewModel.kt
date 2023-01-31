package com.example.booklet.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.booklet.api.APInterface
import com.example.booklet.Dataclass.Books
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(APInterface::class.java)

        fun getMyData(query: String) = flow {
//        var myStringBuilder= Books()

        Log.d("TAG", "getMyData: ")

        val response = retrofitBuilder.getData(query)

        if (response.isSuccessful) {
            val body = response.body()
            emit(body)
        } else {
            emit(null)
        }

    }


    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
}