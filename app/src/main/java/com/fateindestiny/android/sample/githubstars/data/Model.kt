package com.fateindestiny.android.sample.githubstars.data

import android.util.Log
import com.fateindestiny.android.sample.githubstars.GitHubConstants
import com.fateindestiny.android.sample.githubstars.data.remote.APIManager
import com.fateindestiny.android.sample.githubstars.data.remote.SearchResultDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(private val presenter: GitHubConstants.Presenter) {
    fun serchUser(userName: String) {

        APIManager.service().searchUsers(userName).enqueue(
            object : Callback<SearchResultDTO> {
                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                    Log.d("FID", "test :: onFailure")
                }

                override fun onResponse( call: Call<SearchResultDTO>, response: Response<SearchResultDTO> ) {
                    response.body()?.let { presenter.searchResult(it.items) }
                }
            }
        )
    }
} // end of class Model