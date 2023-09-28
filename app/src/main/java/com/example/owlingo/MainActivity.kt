package com.example.owlingo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.owlingo.databinding.ActivityMainBinding
import com.example.owlingo.ui.AppPreferences
import com.example.owlingo.ui.UserInformation
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppPreferences.init(applicationContext)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        UserInformation.userID.observe(this) { userId ->
            if (userId == "1") {
                navView.menu.clear()
                navView.inflateMenu(R.menu.admin_nav_bottom_menu)
            } else if (userId != null && userId !== "1"  && userId !== "-1") {
                navView.menu.clear()
                navView.inflateMenu(R.menu.bottom_nav_menu)
            }
        }
    }
}
