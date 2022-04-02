package com.payconiq.testApplication.ui.gitHubUserList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.base.BaseViewModel
import com.payconiq.testApplication.utils.AppEnum
import com.payconiq.testApplication.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GitHubUserListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<GitHubUserListNavigator>() {

    val pageStart: Int = 1
    var isLastPage: Boolean = false
    var totalPages: Int = 1
    var currentPage: Int = pageStart

    var tempKey = ""

    var searchText = MutableLiveData<String>()
    private val _response: MutableLiveData<NetworkResult<GitHubUser>> = MutableLiveData()
    val response: LiveData<NetworkResult<GitHubUser>> = _response

    private var userList: ArrayList<Items> = arrayListOf()

    var isDataFetched = MutableLiveData<Boolean>(false)


    fun onSearchClick() {
        currentPage = 1
        searchText.value?.let { fetchGitHubUser(it, currentPage.toString()) }
        navigator.hideKeyBoard()
    }

    fun searchGitHubUser() {
        searchText.value?.let { fetchGitHubUser(it, currentPage.toString()) }
    }


    fun fetchGitHubUser(name: String, page: String) {
        viewModelScope.launch {
            isLoading.value = true
            val response = name.let { txt ->
                repository.getUserBySearchGitHub(txt, page)
            }
            response.let {
                _response.value = it
            }
        }
    }

    fun getUserResponse(response: NetworkResult<GitHubUser>) {
        when (response) {
            is NetworkResult.Success -> {
                response.data.let { users ->
                    if (tempKey != searchText.value.toString()) {
                        userList.clear()
                    }
                    if (users.items.isNotEmpty()) {
                        userList.addAll(users.items)
                        navigator.onSetUserInfo(userList)
                    } else {
                        navigator.messageDialog(AppEnum.ERROR_MESSAGE.DATA_NOT_FOUND.data)
                    }
                }
                tempKey = searchText.value.toString()
                isLoading.value = false
            }
            is NetworkResult.InProgress -> {
                isLoading.value = true
            }
            is NetworkResult.Error -> {
                isLoading.value = false
                response.exception.message?.let { navigator.messageDialog(it) }
            }
        }
    }
}