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
        model.serchUser(userName)
    }

    /**
     * 검색된 결과 데이터를 View에 표시하도록 호출.
     *
     * @param list GitHub 유저 데이터 [UserVO] 리스트.
     */
    override fun searchResult(list: List<UserVO>) {
        view.showUserList(list)
    }
} // end of class MainPresenter