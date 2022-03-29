package com.payconiq.testApplication.data.repository

import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.data.remote.dataSource.RemoteDataSource
import com.payconiq.testApplication.data.remote.dataSource.baseDataSource.BaseDataSource
import com.payconiq.testApplication.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class AppRepository @Inject constructor(
    private var dataSource: RemoteDataSource

) : BaseDataSource() {

    suspend fun getGitHubUser(name: String): Flow<Resource<GitHubUser>> {
        return flow {
            emit(dataSource.getAllUser(name))
        }.flowOn(Dispatchers.IO)
    }
}