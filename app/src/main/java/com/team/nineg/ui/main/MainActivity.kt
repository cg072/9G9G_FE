package com.team.nineg.ui.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.postDelayed
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.team.nineg.R
import com.team.nineg.base.BaseActivity
import com.team.nineg.databinding.ActivityMainBinding
import com.team.nineg.ui.calendar.CalendarFragment
import com.team.nineg.ui.mission.MissionFragment
import com.team.nineg.ui.my.MyPageFragment
import dagger.hilt.android.AndroidEntryPoint
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private var isReady = false
    private var isLogin = false

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initSplashScreenListener()
        initNavigation()
        initObserve()

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

    private fun initNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragmentContainer) as? NavHostFragment? ?: return

        navController = host.navController
        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)

        if (isLogin) {
            navGraph.setStartDestination(R.id.missionFragment)
        } else {
            navGraph.setStartDestination(R.id.loginFragment)
        }

        navController.setGraph(navGraph, intent.extras)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d(TAG, "kch destination.navigatorName : ${destination.navigatorName}")
            when (destination.id) {
                R.id.missionFragment -> {
                    Log.d(TAG, "kch destination.id : missionFragment")
                    binding.bottomNavView.visibility = View.VISIBLE
                }

                R.id.calendarFragment -> {
                    Log.d(TAG, "kch destination.id : calendarFragment")
                    binding.bottomNavView.visibility = View.VISIBLE
                }

                R.id.myPageFragment -> {
                    Log.d(TAG, "kch destination.id : myPageFragment")
                    binding.bottomNavView.visibility = View.VISIBLE
                }
                else -> {
                    Log.d(TAG, "kch destination.id : ${destination.id}")
                    binding.bottomNavView.visibility = View.GONE
                }
            }
        }

    }

    private fun initObserve() {
        viewModel.startNavShowCase.observe(this) {
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

    companion object {
        private const val TAG = "MainActivity"
    }
}