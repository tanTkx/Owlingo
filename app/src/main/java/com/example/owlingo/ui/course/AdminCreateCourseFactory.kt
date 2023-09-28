package com.example.owlingo.ui.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminCreateCourseFactory(
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminCreateCourseViewModel::class.java)) {
            return AdminCreateCourseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}