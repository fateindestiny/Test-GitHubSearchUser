package com.fateindestiny.android.sample.githubstars

import com.fateindestiny.android.sample.githubstars.data.UserVO

interface GitHubConstants {
    interface View {
        /**
         * GitHub User 목록을 View에 표시하라는 함수.
         *
         * @param list 유저 목록 [UserVO]
         */
        fun showUserList(list:List<UserVO>)
    } // end of interface View

    interface Presenter {
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
        fun searchResult(list:List<UserVO>)
    } // end of interface Preseneter
} // end of interface GitHubConstants