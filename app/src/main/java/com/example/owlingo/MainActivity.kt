package com.example.owlingo

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.owlingo.databinding.ActivityMainBinding

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(com.example.owlingo.R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

//        val chipGroup = findViewById<ChipGroup>(R.id.courseGroup)
//
//        val chip1 = Chip(this)
//        chip1.text = "Value 1"
//        chip1.setChipBackgroundColorResource(R.color.chip_background_color) // Customize the background color
//
//        chip1.setTextColor(resources.getColor(R.color.chip_text_color)) // Customize the text color
//
//        chipGroup.addView(chip1)
//
//        val chip2 = Chip(this)
//        chip2.text = "Value 2"
//        chip2.setChipBackgroundColorResource(R.color.chip_background_color) // Customize the background color
//
//        chip2.setTextColor(resources.getColor(R.color.chip_text_color)) // Customize the text color
//
//        chipGroup.addView(chip2)
    }
}
