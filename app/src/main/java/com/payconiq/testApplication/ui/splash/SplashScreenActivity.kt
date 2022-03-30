package com.payconiq.testApplication.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.payconiq.testApplication.databinding.ActivitySplashBinding
import com.payconiq.testApplication.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity(), ISplashNavigator {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.splashViewModel = viewModel
        setContentView(binding.root)
        viewModel.setNavigator(this)
        viewModel.waitSplashScreen(3000L)
    }

    override fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}