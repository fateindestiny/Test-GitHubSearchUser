package com.fateindestiny.android.sample.githubstars.presenter

import com.fateindestiny.android.sample.githubstars.data.UserVO

interface GitHubConstants {
    interface View {
        /**
         * GitHub User 목록을 View에 표시하라는 함수.
         *
         * @param list 유저 목록 [UserVO]
         */
        fun showUserList(list:ArrayList<UserVO>)

        /**
         * User 목록 View의 데이터 중 파라메터 데이터와 일치하는 것에 정보를 갱신하는 함수.
         *
         * @param user
         */
        fun updateUserList(user:UserVO)
    } // end of interface View

    interface Presenter {

        /**
         * 즐겨찾기 사용자 여부 조회 함수.
         *
         * @param user 조회할 사용자 [UserVO]
         */
//        fun isFavoritUser(user:UserVO)

        /**
         * 즐겨 찾기 사용자 여부 결과 함수.
         *
         * @param isFavorit 즐겨찾기 여부.
         */
//        fun resultIsFavoritUser(isFavorit:Boolean)


        /**
         * 현재 표시중인 모드를 조회하는 함수.
         *
         * @return 현재 상태의 [Mode] 값.
         */
        fun getCurrentMode() : Mode
        /**
         * 검색 및 유저 목록 표시 모드 변경 함수.
         *
         * @param mode API / Local 모드 값.
         */
        fun changeMode(mode:Mode)

        /**
         * GitHub 유저 검색 함수.
         *
         * @param userName 검색할 유저 이름.
         */
        fun searchUser(userName:String)

        /**
         * 검색 결과 콜백 함수.
         *
         * @param list 검색 결과 [UserVO] 리스트.
         */
        fun searchResult(list:ArrayList<UserVO>)

        /**
         * 유저 즐겨찾기 추가 함수.
         *
         * @param user 유저 정보 [UserVO]
         */
        fun addFavoritUser(user:UserVO)

        /**
         * 유저 즐겨찾기 삭제 함수.
         *
         * @param user 유저 정보 [UserVO]
         */
        fun removeFavoritUser(user:UserVO)

        /**
         * 사용자 목록 아이템 상태를 갱신하는 함수.
         *
         * @param user 갱신할 사용자 데이터 [UserVO]
         */
        fun updateUserList(user:UserVO)
    } // end of interface Presenter
} // end of interface GitHubConstants