package com.fateindestiny.android.sample.githubstars.data

import com.google.gson.annotations.SerializedName

data class UserVO(
    val id: String,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl:String
)