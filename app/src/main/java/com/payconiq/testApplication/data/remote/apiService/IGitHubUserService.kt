package com.payconiq.testApplication.data.remote.apiService

import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitHubUserService {

    @GET("search/users?q=")
    suspend fun getAllGitHubUser(@Query("q") name: String): Response<GitHubUser>

    @GET("users/{loginName}")
    suspend fun getUserInfo(@Path("loginName") loginName: String): Response<UserInfo>
}