package com.fateindestiny.android.sample.githubstars.data

import android.content.ContentValues
import com.fateindestiny.android.sample.githubstars.data.local.DBInfo
import com.google.gson.annotations.SerializedName

/**
 * 사용자 정보 Class.
 * 내부 모듈간의 정보를 주고 받는 데이터의 기본.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
data class UserVO(
    // 사용자 이름.
    @SerializedName("login") val login: String,
    // 사용자 아바타 이미지 URL.
    @SerializedName("avatar_url") val avatarUrl: String
) {

    // 사용자 이름의 초성 정보.
    var initialChars:String? = null

    // 사용자의 즐겨찾기 여부.
    var isFavorit: Boolean = false

    /**
     * Local DB에 저장하기 위해 [ContentValues]로 변환하는 함수.
     *
     * @return [UserVO]의 변환된 [ContentValues]
     */
    fun getContentValues(): ContentValues = ContentValues().apply {
        put(DBInfo.TBL_FAVORIT_USER.LOGIN, login)
        put(DBInfo.TBL_FAVORIT_USER.AVATAR_URL, avatarUrl)
    }

    override fun equals(other: Any?): Boolean =
        other is UserVO && other.login == login && other.avatarUrl == avatarUrl

} // end of class UserVO