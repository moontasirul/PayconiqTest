package com.payconiq.testApplication.data.remote.apiService

import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.UserInfo
import com.payconiq.testApplication.data.remote.ApiEndPoint.Companion.SEARCH_USER_API
import com.payconiq.testApplication.data.remote.ApiEndPoint.Companion.USER_DETAILS_API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitHubUserService {

    @GET(SEARCH_USER_API)
    suspend fun getAllGitHubUser(
        @Query("q") name: String,
        @Query("page") pageNo: String
    ): Response<GitHubUser>

    @GET(USER_DETAILS_API)
    suspend fun getUserInfo(@Path("loginName") loginName: String): Response<UserInfo>
}