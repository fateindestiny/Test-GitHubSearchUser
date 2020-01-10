package com.fateindestiny.android.sample.githubstars.data

import android.content.ContentValues
import com.fateindestiny.android.sample.githubstars.data.local.DBInfo
import com.google.gson.annotations.SerializedName

data class UserVO(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
) {

    var initialChars:String? = null

    var isFavorit: Boolean = false

    fun getContentValues(): ContentValues = ContentValues().apply {
        put(DBInfo.TBL_FAVORIT_USER.LOGIN, login)
        put(DBInfo.TBL_FAVORIT_USER.AVATAR_URL, avatarUrl)
    }

    override fun equals(other: Any?): Boolean =
        other is UserVO && other.login == login && other.avatarUrl == avatarUrl

} // end of class UserVO