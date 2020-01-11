package com.fateindestiny.android.sample.githubstars.data.remote

import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [APIManager]의 [Retrofit] 에서 사용할 Service Interface.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
interface APIService {

    /**
     * 사용자 조회 API.
     *
     * @param userName 조회할 사용자 이름
     * @param page 조회할 사용자 정보의 페이지 번호(기본값 : 1)
     */
    @GET("/search/users")
    fun searchUsers(@Query("q") userName: String, @Query("page") page: Int = 1): Call<SearchResultDTO>
} // end of interface APIService