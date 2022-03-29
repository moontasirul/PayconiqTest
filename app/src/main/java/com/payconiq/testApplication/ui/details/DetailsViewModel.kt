package com.payconiq.testApplication.ui.details

import androidx.lifecycle.MutableLiveData
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<IDetailsNavigator>() {

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var userImage = MutableLiveData<String>()

}