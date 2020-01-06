package com.fateindestiny.android.sample.githubstars.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/search/users")
    fun searchUsers(@Query("q") userName: String): Call<SearchResultDTO>
}