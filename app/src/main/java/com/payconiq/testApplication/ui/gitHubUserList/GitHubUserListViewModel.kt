package com.payconiq.testApplication.ui.gitHubUserList

import androidx.databinding.ObservableField
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GitHubUserListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<GitHubUserListNavigator>() {

    var isDataFetching = ObservableField(false)



}