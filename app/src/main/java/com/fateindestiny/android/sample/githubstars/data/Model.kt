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

/**
 * [GitHubConstants.Presenter]와 연동하여 데이터를 조회 및 처리를 실행하는 Class.
 *
 * 1. GitHub API를 통해 사용자 조회
 * 2. [DBHelper]를 사용하여 Local DB 사용
 *   - 사용자 조회
 *   - 사용자 삭제
 *   - 사용자 추가
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
class Model(private val presenter: GitHubConstants.Presenter) {
    private val dbHelper = DBHelper

    private var cacheUserName: String? = null
    private var cacheSearchPage: Int = 1

    /**
     * Local DB 상의 사용자를 조회하는 함수.
     *
     * @param userName 조회할 사용자 이름.
     */
    fun searchUserByLocal(userName: String) {
        presenter.searchResult(dbHelper.selectFavoritUser(userName))
    }

    /**
     * API로 호출하여 사용자를 조회하는 함수.
     *
     * @param userName 조회할 사용자 이름.
     */
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
                                        this.forEach {
                                            // 즐겨찾기 여부를 DB에서 조회하여 플래그값 처리.
                                            it.isFavorit = isFavoritUser(it)
                                            // 사용자 이름을 통해 초성 정보를 가져와 필드에 저장.
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

    /**
     * [searchUserByAPI] 호출될 때 저장되어 있는 [cacheUserName]과 [cacheSearchPage]를 통해
     * 추가로 사용자 정보를 조회하기 위한 함수.
     */
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
                                // 오름차순 정렬.
                                ArrayList(it.items.sortedWith(compareBy({ it.login }))
                                    .apply {
                                        this.forEach {
                                            // 즐겨찾기 여부를 DB에서 조회하여 플래그값 처리.
                                            it.isFavorit = isFavoritUser(it)
                                            // 사용자 이름을 통해 초성 정보를 가져와 필드에 저장.
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

    /**
     * 즐겨찾기 추가하여 Local DB에 데이터 삽입하는 함수.
     *
     * @param user 추가할 사용자 [UserVO]
     */
    fun addFavoritUser(user: UserVO) {
        if (dbHelper.insertFavoritUser(user) > 0) {
            user.isFavorit = true
            presenter.updateUserList(user)
        }
    }

    /**
     * 즐겨찾기 해제하여 Local DB에서 삭제하는 함수.
     *
     * @param user 삭제할 사용자 [UserVO]
     */
    fun removeFavoritUser(user: UserVO) {
        if (dbHelper.deleteFavoritUser(user) > 0) {
            user.isFavorit = false
            presenter.updateUserList(user)
        }
    }

    /**
     * 즐겨찾기 여부를 조회하는 함수.
     *
     * @param user 조회할 사용자 [UserVO]
     * @return 즐겨찾기 여부 [Boolean]
     */
    fun isFavoritUser(user: UserVO): Boolean =
        dbHelper.hasFavoritUser(user.login)

} // end of class Model