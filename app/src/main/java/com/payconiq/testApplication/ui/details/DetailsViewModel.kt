package com.payconiq.testApplication.ui.details

import androidx.lifecycle.MutableLiveData
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<IDetailsNavigator>() {

    var title = MutableLiveData<String>()
    var userAvatar = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var userImage = MutableLiveData<String>()


    fun setUserInfo(item: Items) {
        item.avatarUrl?.let { avatar ->
            userAvatar.value = avatar
        }
    }
}