package com.payconiq.testApplication.ui.gitHubUserList

import com.payconiq.testApplication.Items
import com.payconiq.testApplication.ui.base.IBaseNavigator

interface GitHubUserListNavigator : IBaseNavigator {
    fun onSetUserInfo(userList: ArrayList<Items>)
    fun messageDialog(message: String)
}