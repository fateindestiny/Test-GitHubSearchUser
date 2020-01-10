package com.fateindestiny.android.sample.githubstars.data

import android.util.Log
import com.fateindestiny.android.sample.githubstars.data.local.DBHelper
import com.fateindestiny.android.sample.githubstars.data.remote.APIManager
import com.fateindestiny.android.sample.githubstars.data.remote.SearchResultDTO
import com.fateindestiny.android.sample.githubstars.presenter.GitHubConstants
import com.fateindestiny.android.sample.githubstars.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(private val presenter: GitHubConstants.Presenter) {
    private val dbHelper = DBHelper

    private var cacheUserName: String? = null
    private var cacheSearchPage: Int = 1

    fun searchUserByLocal(userName: String) {
        presenter.searchResult(dbHelper.selectFavoritUser(userName))
    }

    fun searchUserByAPI(userName: String) {
        cacheUserName = userName
        cacheSearchPage = 1
        APIManager.service().searchUsers(userName).apply {
            enqueue(object : Callback<SearchResultDTO> {
                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                    Log.d("FID", "test :: onFailure")
                }

                override fun onResponse( call: Call<SearchResultDTO>, response: Response<SearchResultDTO> ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            presenter.searchResult(
                                ArrayList(it.items.sortedWith(compareBy({ it.login }))
                                    .apply {
                                        // 즐겨찾기 여부를 DB에서 조회하여 플래그값 처리.
                                        this.forEach {
                                            it.isFavorit = isFavoritUser(it)
                                            it.initialChars = Util.getInitialChar(it.login)
                                        }
                                    })
                            )
                        }
                    }
                }
            })
        }
    }

    fun searchMoreByAPI() {
        val userName = cacheUserName ?: return
        cacheSearchPage++
        APIManager.service().searchUsers(userName, cacheSearchPage).apply {
            enqueue(object : Callback<SearchResultDTO> {
                override fun onFailure(call: Call<SearchResultDTO>, t: Throwable) {
                    Log.d("FID", "test :: onFailure")
                }

                override fun onResponse( call: Call<SearchResultDTO>, response: Response<SearchResultDTO> ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            presenter.searchMoreResult(
                                ArrayList(it.items.sortedWith(compareBy({ it.login }))
                                    .apply {
                                        // 즐겨찾기 여부를 DB에서 조회하여 플래그값 처리.
                                        this.forEach {
                                            it.isFavorit = isFavoritUser(it)
                                            it.initialChars = Util.getInitialChar(it.login)
                                        }
                                    })
                            )
                        }
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