package com.fateindestiny.android.sample.githubstars.presenter

import com.fateindestiny.android.sample.githubstars.data.Model
import com.fateindestiny.android.sample.githubstars.data.UserVO

class MainPresenter(private val view: GitHubConstants.View) : GitHubConstants.Presenter {

    private val model: Model by lazy { Model(this) }
    private var currentMode = Mode.API

    override fun getCurrentMode(): Mode = currentMode

    override fun changeMode(mode: Mode) {
        currentMode = mode
    }

    /**
     * View 에서 호출된 유저 검색 함수.
     *
     * @param userName 검색할 사용자 이름.
     */
    override fun searchUser(userName: String) {
        when (currentMode) {
            Mode.API -> {
                // API 모드는 검색어가 없다면 굳이 호출하지 않아도 됨.
                if (userName.isNotEmpty()) {
                    model.searchUserByAPI(userName)
                }
            }
            Mode.LOCAL -> {
                model.searchUserByLocal(userName)
            }
        }
    }

    /**
     * 검색된 결과 데이터를 View에 표시하도록 호출.
     *
     * @param list GitHub 유저 데이터 [UserVO] 리스트.
     */
    override fun searchResult(list: ArrayList<UserVO>) {
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

    /**
     * 사용자 목록 아이템 상태를 갱신하는 함수.
     *
     * @param user 갱신할 사용자 데이터 [UserVO]
     */
    override fun updateUserList(user: UserVO) {
        view.updateUserList(user)
    }
} // end of class MainPresenter