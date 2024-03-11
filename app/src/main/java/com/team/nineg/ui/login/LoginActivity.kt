package com.team.nineg.ui.login

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.postDelayed
import com.team.nineg.R
import com.team.nineg.base.BaseActivity
import com.team.nineg.base.UiState
import com.team.nineg.databinding.ActivityLoginBinding
import com.team.nineg.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    private var isReady = false

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initSplashScreenListener()
        observe()
        listener()
        binding.root.postDelayed(2000) {
            isReady = true
        }
    }

    private fun initSplashScreenListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    1f,
                    0f
                ).run {
                    interpolator = AnticipateInterpolator()
                    duration = 300L
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }

        binding.root.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (isReady) {
                    binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })
    }

    private fun listener() {
        binding.activityLoginKakaoBtn.setOnClickListener {
            val ssaid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            viewModel.initUserData(ssaid)
        }
    }

    private fun observe() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    ActivityUtil.startMainActivity(this)
                    finish()
                }
                is UiState.Error -> {
                    Toast.makeText(this, R.string.login_error_message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

}