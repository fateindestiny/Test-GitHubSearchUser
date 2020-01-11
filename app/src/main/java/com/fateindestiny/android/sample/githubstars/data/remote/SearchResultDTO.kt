package com.fateindestiny.android.sample.githubstars.data.remote

import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.google.gson.annotations.SerializedName

/**
 * [APIService.searchUsers] API를 통해 조회된 데이터를 파싱할 data cless.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
data class SearchResultDTO(
    // 요청한 검색 조건의 총 사용자 정보 수.
    @SerializedName("total_count") val totalCount:Int,

    @SerializedName("incomplete_results") val incompleteResults:Boolean,

    // 사용자 정보 리스트.
    @SerializedName("items") val items:ArrayList<UserVO>
) // end of class SearchResultDTO
