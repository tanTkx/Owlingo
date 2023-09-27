package com.example.owlingo

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.owlingo.databinding.ActivityMainBinding

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(com.example.owlingo.R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        bottomNavigationView = findViewById(R.id.nav_view)
    }

    fun hideBottomNavigation() {
        bottomNavigationView?.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bottomNavigationView?.visibility = View.VISIBLE
    }
}
