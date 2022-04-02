package com.payconiq.testApplication.data.repository

import android.util.Log
import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.UserInfo
import com.payconiq.testApplication.data.remote.apiService.IGitHubUserService
import com.payconiq.testApplication.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.HttpException
import javax.inject.Inject

@ActivityRetainedScoped
class AppRepository @Inject constructor(
    private var dataSource: IGitHubUserService
) : BaseRepository() {


    companion object {
        private val TAG = AppRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }


    suspend fun getUserBySearchGitHub(name: String, page: String): NetworkResult<GitHubUser> {
        var networkResult: NetworkResult<GitHubUser> = handleSuccess(GitHubUser())
        try {
            val response = dataSource.getAllGitHubUser(name, page)
            response.let {
                it.body()?.let { userResponse ->
                    networkResult = handleSuccess(userResponse)
                }
                it.errorBody()?.let { responseErrorBody ->
                    if (responseErrorBody is HttpException) {
                        responseErrorBody.response()?.code()?.let { errorCode ->
                            networkResult = handleException(errorCode)
                        }
                    } else networkResult = handleException(GENERAL_ERROR_CODE)
                }
            }
        } catch (error: HttpException) {
            Log.e(TAG, "Error: ${error.message}")
            return handleException(error.code())
        }
        return networkResult
    }

    suspend fun getGitHubUserInfo(login: String): NetworkResult<UserInfo> {
        var networkResult: NetworkResult<UserInfo> = handleSuccess(UserInfo())
        try {
            val userData = dataSource.getUserInfo(login)
            userData.let {
                it.body()?.let { userResponse ->
                    networkResult = handleSuccess(userResponse)
                }
                it.errorBody()?.let { responseErrorBody ->
                    if (responseErrorBody is HttpException) {
                        responseErrorBody.response()?.code()?.let { errorCode ->
                            networkResult = handleException(errorCode)
                        }
                    } else networkResult = handleException(GENERAL_ERROR_CODE)
                }
            }
        } catch (error: HttpException) {
            return handleException(error.code())
        }
        return networkResult
    }
}