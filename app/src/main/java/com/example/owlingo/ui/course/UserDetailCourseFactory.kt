package com.example.owlingo.ui.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserDetailCourseFactory(
    private val courseId: Int,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailCourseViewModel::class.java)) {
            return UserDetailCourseViewModel(courseId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}