package com.payconiq.testApplication.ui.splash

import android.os.Handler
import android.os.Looper
import com.payconiq.testApplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<ISplashNavigator>() {

    /**
     * splash screen waiting handler;
     * It'll change later after 3000 millisecond.
     */
    fun waitSplashScreen(timeInMillisecond: Long) {
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                navigator.openMainActivity()
            }, timeInMillisecond)
        }
    }
}