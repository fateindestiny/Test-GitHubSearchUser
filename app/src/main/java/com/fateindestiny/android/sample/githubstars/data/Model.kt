package com.fateindestiny.android.sample.githubstars.data

import android.util.Log
import com.fateindestiny.android.sample.githubstars.presenter.GitHubConstants
import com.fateindestiny.android.sample.githubstars.data.local.DBHelper
import com.fateindestiny.android.sample.githubstars.data.remote.APIManager
import com.fateindestiny.android.sample.githubstars.data.remote.SearchResultDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(private val presenter: GitHubConstants.Presenter) {
    private var cache: Call<SearchResultDTO>? = null
    private val dbHelper = DBHelper


    fun searchUserByLocal(userName: String) {
        presenter.searchResult(dbHelper.selectFavoritUser(userName))
    }

    fun searchUserByAPI(userName: String) {
        cache = APIManager.service().searchUsers(userName).apply {
            enqueue(object : Callback<SearchResultDTO> {
                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                    Log.d("FID", "test :: onFailure")
                }

                override fun onResponse(
                    call: Call<SearchResultDTO>,
                    response: Response<SearchResultDTO>
                ) {
                    response.body()?.let { it ->
                        presenter.searchResult(it.items.apply {
                            // 즐겨찾기 여부를 DB에서 조회하여 플래그값 처리.
                            this.forEach {
                                it.isFavorit = isFavoritUser(it)
                            }
                        })
                    }
                }
            })
        }
    }

    fun addFavoritUser(user: UserVO) {
        if (dbHelper.insertFavoritUser(user) > 0) {
            user.isFavorit = true
            presenter.updateUserList(user)
        }
    }

    fun removeFavoritUser(user: UserVO) {
        if (dbHelper.deleteFavoritUser(user) > 0) {
            user.isFavorit = false
            presenter.updateUserList(user)
        }
    }

    fun isFavoritUser(user: UserVO): Boolean =
        dbHelper.hasFavoritUser(user.login)

} // end of class Model