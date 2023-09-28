package com.example.owlingo.ui.schedule

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.owlingo.ui.community.EditCommentViewModel



class EditScheduleFactory(
    private val scheduleID: Int,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditScheduleViewModel::class.java)) {
            return EditScheduleViewModel(scheduleID, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}