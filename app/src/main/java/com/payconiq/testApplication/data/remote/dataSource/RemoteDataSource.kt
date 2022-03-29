package com.payconiq.testApplication.data.remote.dataSource

import com.payconiq.testApplication.data.remote.apiService.IGitHubUserService
import com.payconiq.testApplication.data.remote.dataSource.baseDataSource.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubUserService: IGitHubUserService
) : BaseDataSource() {
    suspend fun getAllUser(name: String) = getResult { gitHubUserService.getAllGitHubUser(name) }
}