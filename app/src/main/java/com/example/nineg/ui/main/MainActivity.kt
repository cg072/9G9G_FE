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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment? ?: return

        val navController = host.navController
        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }

            Log.d("MainActivity", "Navigated to $dest")
        }
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
}