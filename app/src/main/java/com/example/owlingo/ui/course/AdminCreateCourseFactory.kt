package com.example.owlingo.ui.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminCreateCourseFactory(
    private val userId: Int,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminAllCourseViewModel::class.java)) {
            return AdminAllCourseViewModel(userId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}