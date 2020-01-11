package com.fateindestiny.android.sample.githubstars.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * GitHub와 API 연동을 하기 위한 Class.
 * [Retrofit] 으로 구현.
 *
 * 기능
 * 1. API 연동 Client 제공.
 * 2. API 연동 Service 제공.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
object APIManager {

    fun client() = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun service() = client().create(APIService::class.java)
} // end of class APIManager