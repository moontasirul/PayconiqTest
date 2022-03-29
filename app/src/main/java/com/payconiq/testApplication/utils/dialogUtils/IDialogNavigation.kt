package com.payconiq.testApplication.utils.dialogUtils

import com.payconiq.testApplication.ui.base.IBaseNavigator

interface IDialogNavigation : IBaseNavigator {
    fun onNext()
    fun onPositive()
    fun onCancel()
}