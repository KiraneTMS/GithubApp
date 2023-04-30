package com.arine.githubapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Search {

    @Headers("Authorization: token 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")
    @GET("/search/users")
    suspend fun getUsers(@Query("q") key: String ): Response<SearchUsers>

    @GET("/users/{value}")
    suspend fun getDetail(@Path("value") value: String): Response<DetailUsers>

    @GET("/users/{value}/followers")
    suspend fun getUserFollowers(@Path("value") username: String): Response<List<Follows>>

    @GET("/users/{value}/following")
    suspend fun getUserFollowigs(@Path("value") username: String): Response<List<Follows>>
}