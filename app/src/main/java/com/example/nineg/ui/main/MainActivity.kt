package com.example.nineg.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nineg.R
import com.example.nineg.base.BaseActivity
import com.example.nineg.databinding.ActivityMainBinding
import com.example.nineg.navigation.MaintainStatusNavigator
import com.example.nineg.ui.mission.MissionViewModel

import dagger.hilt.android.AndroidEntryPoint
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val missionViewModel: MissionViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)

        initNavigation()
        initObserve()
    }

    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment

        val navController = host.navController
        val navigator =
            MaintainStatusNavigator(this, host.childFragmentManager, R.id.navHostFragmentContainer)
        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.main_navigation)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }

            Log.d("MainActivity", "Navigated to $dest")
        }

        initObserve()
    }

    private fun initSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepVisibleCondition {
            // true == 지속 false == 종료
            !viewModel.isReady
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun initObserve() {
        missionViewModel.startNavShowCase.observe(this) {
            tutorialBottomNav()
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
}