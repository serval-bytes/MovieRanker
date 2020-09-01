package com.nmccloud.project3

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nmccloud.project3.database.MovieRepository
import com.nmccloud.project3.ui.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_settings, R.id.navigation_info
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //for navigation animations
        navView.menu[0].setOnMenuItemClickListener {
            navController.navigate(R.id.action_global_navigation_settings)
            true
        }

        navView.menu[1].setOnMenuItemClickListener {
            navController.navigate(R.id.action_global_navigation_info)
            true
        }
    }

    fun navToDetailView()
    {
        findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_detailFragment)
    }

    fun navToReviewView()
    {
        findNavController(R.id.nav_host_fragment).navigate(R.id.action_detailFragment_to_reviewFragment)
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()

    companion object{
        const val YEAR_ZERO = 1900
        val YEARS = (2020 downTo YEAR_ZERO).toList().toTypedArray()
        const val SHOW_HIDE_WATCHED = "show_hide_watched"
    }

}
