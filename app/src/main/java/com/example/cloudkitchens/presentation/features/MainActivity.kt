package com.example.cloudkitchens.presentation.features

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chipnavigationapp.TabsFragment
import com.example.cloudkitchens.R
import com.example.cloudkitchens.presentation.features.orderstatistics.OrderStatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.OkHttpClient
import java.io.IOException
import okhttp3.Request

/**
 * Main activity of the application, responsible for handling bottom navigation and fragment management.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_chip_screen -> {
                    openFragment(TabsFragment())
                    true
                }
                R.id.nav_empty_screen -> {
                    openFragment(OrderStatisticsFragment())
                    true
                }
                else -> false
            }
        }

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_chip_screen
        }


    }

    /**
     * Opens the specified fragment.
     *
     * @param fragment The fragment to open.
     */
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
