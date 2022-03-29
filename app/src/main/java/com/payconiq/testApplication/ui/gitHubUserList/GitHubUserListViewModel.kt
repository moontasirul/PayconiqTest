package com.payconiq.testApplication.ui.gitHubUserList

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.base.BaseViewModel
import com.payconiq.testApplication.utils.AppEnum
import com.payconiq.testApplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GitHubUserListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<GitHubUserListNavigator>() {

    var isDataFetching = ObservableField(false)

    private val _response: MutableLiveData<Resource<GitHubUser>> = MutableLiveData()
    val response: LiveData<Resource<GitHubUser>> = _response

    var userList: ArrayList<Items> = arrayListOf()

    fun fetchGitHubUserResponse() = viewModelScope.launch {
        repository.getGitHubUser("q").collect { values ->
            _response.value = values
        }
    }


    fun getUserResponse(response: Resource<GitHubUser>) {
        when (response.status.name) {
            AppEnum.API_CALL_STATUS.SUCCESS.name -> {
                response.data?.let {
                    userList.addAll(it.items)
                }
                isLoading.value = false
            }
            AppEnum.API_CALL_STATUS.ERROR.name -> {
                isLoading.value = false
                print(response.message)
            }
            AppEnum.API_CALL_STATUS.LOADING.name -> {
                isLoading.value = true
                print(response.message)
            }
        }
    }
}