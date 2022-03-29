package com.payconiq.testApplication.utils.dialogUtils

import androidx.databinding.ObservableField
import com.payconiq.testApplication.ui.base.BaseViewModel

class CustomDialogViewModel : BaseViewModel<IDialogNavigation>() {

    var dialogTitle = ObservableField<String>()
    var dialogMessage = ObservableField<String>()
    var positiveBtnText = ObservableField<String>()
    var closeBtnText = ObservableField<String>()

    fun onNext() {
        navigator.onNext()
    }

    fun onPositive() {
        navigator.onPositive()
    }

    fun onCancel() {
        navigator.onCancel()
    }


}