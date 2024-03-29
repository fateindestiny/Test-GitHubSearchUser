package com.fateindestiny.android.sample.githubstars.presenter

import com.fateindestiny.android.sample.githubstars.data.Model
import com.fateindestiny.android.sample.githubstars.data.UserVO

class MainPresenter(private val view: GitHubConstants.View) : GitHubConstants.Presenter {

    /**
     * 데이터를 요청하기 위한 [Model] 객체.
     */
    private val model: Model by lazy { Model(this) }

    /**
     * 모드를 저장할 Field.
     * 기본 값은 [Mode.API]
     */
    private var currentMode = Mode.API

    /**
     * 현재 [Mode]를 조회하는 함수.
     *
     * @return 현재 [Mode] 값.
     */
    override fun getCurrentMode(): Mode = currentMode

    /**
     * API, Local 모드를 변경하는 함수.
     *
     * @param mode 변경할 [Mode].
     */
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
     * View에서 요청하는 추가 사용자 정보 조회 함수.
     */
    override fun searchMore() {
        model.searchMoreByAPI()
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
     * 추가 검색 결과 콜백 함수.
     * API 검색 리스트에 아이템을 추가하도록 처리.
     *
     * @param list 검색 결과 [UserVO] 리스트.
     */
    override fun searchMoreResult(list: ArrayList<UserVO>) {
        view.addUserList(list)
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