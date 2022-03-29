package com.payconiq.testApplication.data.remote.apiService

import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.data.remote.ApiEndPoint.Companion.SEARCH_USER_API
import retrofit2.Response
import retrofit2.http.GET

interface IGitHubUserService {

    @GET(SEARCH_USER_API)
    suspend fun getAllGitHubUser(): Response<GitHubUser>

}