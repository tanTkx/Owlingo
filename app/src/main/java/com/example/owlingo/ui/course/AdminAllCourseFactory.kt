package com.example.owlingo.ui.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminAllCourseFactory(
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminAllCourseViewModel::class.java)) {
            return AdminAllCourseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}