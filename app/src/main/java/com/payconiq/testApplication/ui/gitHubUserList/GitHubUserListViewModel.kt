package com.payconiq.testApplication.ui.gitHubUserList

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


    private val _response: MutableLiveData<Resource<GitHubUser>> = MutableLiveData()
    val response: LiveData<Resource<GitHubUser>> = _response

    var userList: ArrayList<Items> = arrayListOf()

    var isDataFetched = MutableLiveData<Boolean>(false)

    fun fetchGitHubUser(userName: String) = viewModelScope.launch {
        isLoading.value = true
        repository.getGitHubUser(userName).collect { values ->
            _response.value = values
        }
    }


    fun getUserResponse(response: Resource<GitHubUser>) {
        when (response.status.name) {
            AppEnum.API_CALL_STATUS.SUCCESS.name -> {
                response.data?.let {
                    if (userList.isNotEmpty()) {
                        userList.clear()
                    }
                    if (it.items.isNotEmpty()) {
                        userList.addAll(it.items)
                        navigator.onSetUserInfo(userList)
                    } else {
                        navigator.messageDialog("Data not found...")
                    }

                }
                isLoading.value = false
            }
            AppEnum.API_CALL_STATUS.ERROR.name -> {
                isLoading.value = false
                response.message?.let { navigator.messageDialog(it) }
            }
            AppEnum.API_CALL_STATUS.LOADING.name -> {
                isLoading.value = true
            }
        }
    }
}