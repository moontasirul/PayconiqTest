package com.payconiq.testApplication.ui.gitHubUserList

import androidx.lifecycle.MutableLiveData
import com.payconiq.testApplication.Items


class GitHubUserItemViewModel(
    position: Int,
    mUserItemModel: Items,
    listener: UserItemViewModelListener
) {

    private val mListener: UserItemViewModelListener = listener

    var userItem: MutableLiveData<Items> = MutableLiveData(mUserItemModel)


    fun onItemClick() {
        userItem.value?.let { mListener.onContentDetails(it) }
    }

    interface UserItemViewModelListener {
        fun onContentDetails(mUserListModel: Items)
    }
}