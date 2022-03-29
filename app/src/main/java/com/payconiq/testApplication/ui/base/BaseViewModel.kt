package com.payconiq.testApplication.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<N : IBaseNavigator> : ViewModel() {

    var isLoading = MutableLiveData(false)
    private lateinit var mNavigator: N

    /**
     * Get Navigator
     */
    protected val navigator: N
        get() {
            return mNavigator
        }

    /**
     * Set Navigator
     */
    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }
}