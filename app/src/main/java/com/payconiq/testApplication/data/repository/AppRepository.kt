package com.payconiq.testApplication.data.repository

import com.payconiq.testApplication.data.remote.dataSource.RemoteDataSource
import com.payconiq.testApplication.data.remote.dataSource.baseDataSource.BaseDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AppRepository @Inject constructor(
    private var dataSource: RemoteDataSource

) : BaseDataSource() {

}