package com.example.fetchrewardstakehome.api

import com.example.fetchrewardstakehome.model.Item
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ItemsAPI {
    @GET("/hiring.json")
    suspend fun getItems() : Response<List<Item>>

    companion object {
        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com"

        // Create the ItemsAPI using Retrofit
        fun getInstance() : ItemsAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ItemsAPI::class.java)
        }
    }
}