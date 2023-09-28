package com.example.owlingo.ui.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminEditCourseFactory(
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminEditCourseViewModel::class.java)) {
            return AdminEditCourseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}