package com.fateindestiny.android.sample.githubstars

import com.fateindestiny.android.sample.githubstars.data.Model
import com.fateindestiny.android.sample.githubstars.data.UserVO

class MainPresenter(private val view: GitHubConstants.View) : GitHubConstants.Presenter {

    private val model: Model by lazy { Model(this) }

    /**
     * View 에서 호출된 유저 검색 함수.
     *
     * @param userName 검색할 사용자 이름.
     */
    override fun searchUser(userName: String) {
        if(userName.isNotEmpty()) {
            model.serchUser(userName)
        }
    }

    /**
     * 검색된 결과 데이터를 View에 표시하도록 호출.
     *
     * @param list GitHub 유저 데이터 [UserVO] 리스트.
     */
    override fun searchResult(list: List<UserVO>) {
        view.showUserList(list)
    }

    /**
     * 유저 즐겨찾기 추가 함수.
     *
     * @param user 유저 정보 [UserVO]
     */
    override fun addFavoritUser(user: UserVO) {
        model.addFavoritUser(user)
    }

    /**
     * 유저 즐겨찾기 삭제 함수.
     *
     * @param user 유저 정보 [UserVO]
     */
    override fun removeFavoritUser(user: UserVO) {
        model.removeFavoritUser(user)
    }
} // end of class MainPresenter