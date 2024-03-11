package com.team.nineg.ui.main

import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.team.nineg.R
import com.team.nineg.base.BaseActivity
import com.team.nineg.databinding.ActivityMainBinding
import com.team.nineg.navigation.MaintainStatusNavigator
import dagger.hilt.android.AndroidEntryPoint
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        initObserve()
        val ssaid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.initUserData(ssaid)
    }

    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment

        navController = host.navController
        val navigator =
            MaintainStatusNavigator(this, host.childFragmentManager, R.id.navHostFragmentContainer)
        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.main_navigation)
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun initObserve() {
        viewModel.startNavShowCase.observe(this) {
            tutorialBottomNav()
        }

        viewModel.isNetworkError.observe(this) { isSuccess ->
            // TODO : navController.navigate 해당 로직 동작시 사이드 이펙트 발생
//            if (isSuccess) {
//                navController.navigate(R.id.missionFragment)
//            } else {
//                navController.navigate(R.id.networkErrorFragment)
//            }
        }
    }

    private fun tutorialBottomNav() {
        GuideView.Builder(this)
            .setTitle(getString(R.string.calendar_title))
            .setContentText(getString(R.string.TEXT_TUTORIAL_CALENDAR))
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.bottomNavView)
            .setGravity(Gravity.center)
            .build()
            .show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}