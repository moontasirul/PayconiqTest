package com.payconiq.testApplication.data.remote

class ApiEndPoint {
    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val API_HEADER = "application/vnd.github.v3+json"
        const val SEARCH_USER_API = "search/users?"
        const val USER_DETAILS_API = "users/{loginName}"
    }
}