package com.payconiq.testApplication.ui.details

import com.payconiq.testApplication.ui.base.IBaseNavigator

interface IDetailsNavigator : IBaseNavigator {
    fun messageDialog(message: String)
}