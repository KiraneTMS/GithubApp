package com.arine.githubapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy

object RetrofitInstance {
    private lateinit var search: Search

    val getSearchUsers by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Search::class.java)
    }

    val getDetailUsers by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.arine.githubapp.Search::class.java)
    }

    val getUserFollowers by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.arine.githubapp.Search::class.java)
    }

    val getUserFollowigs by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.arine.githubapp.Search::class.java)
    }

    fun getAPIClient(): Search {
        if (!::search.isInitialized) {
            val client = OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            search = retrofit.create(Search::class.java)
        }
        return search
    }


}