package com.fateindestiny.android.sample.githubstars.data.remote

import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.google.gson.annotations.SerializedName

data class SearchResultDTO(
    @SerializedName("total_count") val totalCount:Int,
    @SerializedName("incomplete_results") val incompleteResults:Boolean,
    @SerializedName("items") val items:List<UserVO>
)
