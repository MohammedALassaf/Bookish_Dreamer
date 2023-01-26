package com.example.booklet.api

import com.example.booklet.Dataclass.Books
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APInterface {

    @GET("volumes")
    suspend fun getData(
        @Query("q") volume: String = "harry%20potter"
    ): Response<Books>
}