package com.fateindestiny.android.sample.githubstars.data

import android.util.Log
import com.fateindestiny.android.sample.githubstars.MainPresenter
import com.fateindestiny.android.sample.githubstars.data.remote.APIManager
import com.fateindestiny.android.sample.githubstars.data.remote.SearchResultDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(private val presenter: MainPresenter) {

    fun serchUser(userName: String) {

        APIManager.service().searchUsers(userName).enqueue(
            object : Callback<SearchResultDTO> {
                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                    Log.d("FID", "test :: onFailure")
                }

                override fun onResponse(
                    call: Call<SearchResultDTO>,
                    response: Response<SearchResultDTO>
                ) {
                    Log.d("FID", "test :: onResponse :: response=${response.body()}")
                    val body = response.body()
                    if(body is SearchResultDTO) {
                        body.items.forEach {
                            Log.d("FID", "test :: it=$it")
                        }
                    }
                }
            }
        )

    }
} // end of class Model