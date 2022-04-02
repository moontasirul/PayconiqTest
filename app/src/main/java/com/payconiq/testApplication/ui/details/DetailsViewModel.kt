package com.payconiq.testApplication.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.UserInfo
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.base.BaseViewModel
import com.payconiq.testApplication.utils.AppEnum
import com.payconiq.testApplication.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<IDetailsNavigator>() {

    private val _response: MutableLiveData<NetworkResult<UserInfo>> = MutableLiveData()
    val response: LiveData<NetworkResult<UserInfo>> = _response

    var title = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var userLocation = MutableLiveData<String>()
    var userAvatar = MutableLiveData<String>()
    var follower = MutableLiveData<String>()
    var following = MutableLiveData<String>()
    var publicRepo = MutableLiveData<String>()
    var userBio = MutableLiveData<String>()


    fun fetchUserInfo(item: Items) = viewModelScope.launch {
        isLoading.value = true
        item.login?.let {
            val userResponse = repository.getGitHubUserInfo(it)
            userResponse.let { values ->
                _response.value = values
            }
        }
    }


    fun getUserInfoResponse(response: NetworkResult<UserInfo>) {
        when (response) {
            is NetworkResult.Success -> {
                response.data.let {
                    setUserData(it)
                }
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

    private fun setUserData(it: UserInfo) {
        userAvatar.value = it.avatarUrl.toString()
        title.value = it.login.toString()
        name.value = it.name.toString()

        if (it.location != null) {
            userLocation.value = it.location.toString()
        } else {
            userLocation.value = AppEnum.ERROR_MESSAGE.LOCATION_NULL.data
        }
        follower.value = it.followers.toString()
        following.value = it.following.toString()
        publicRepo.value = it.publicRepos.toString()
        if (it.bio != null) {
            userBio.value = it.bio.toString()
        } else {
            userBio.value = AppEnum.ERROR_MESSAGE.BIO_NULL.data
        }
    }

}