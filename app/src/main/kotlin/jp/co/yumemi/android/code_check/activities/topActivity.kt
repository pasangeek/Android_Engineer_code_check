/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.ActivityTopBinding


/**
 * Top-level activity in the application.
 * Annotated with [@AndroidEntryPoint] for Hilt dependency injection.
 */
@AndroidEntryPoint
class TopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityTopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Find the NavController from the NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Initialize the BottomNavigationView
        navController = navHostFragment.navController
        val navView: BottomNavigationView = binding.navView

        // Configure the AppBarConfiguration with the top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.favouriteRepositoryFragment, R.id.settingFragment
            )
        )
        // Setup the ActionBar with the Navigation Controller and AppBarConfiguration
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Setup the BottomNavigationView with the Navigation Controller
        navView.setupWithNavController(navController)

    }

    /**
     * Handle the navigation up event.
     * @return True if navigation up is handled, false otherwise.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Handle the back button press event.
     */
    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}
