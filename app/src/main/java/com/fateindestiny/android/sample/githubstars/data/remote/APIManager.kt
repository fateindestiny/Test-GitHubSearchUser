package com.fateindestiny.android.sample.githubstars.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIManager {

    fun client() = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun service() = client().create(APIService::class.java)
}