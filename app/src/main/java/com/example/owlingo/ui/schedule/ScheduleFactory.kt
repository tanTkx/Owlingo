package com.example.owlingo.ui.schedule

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.owlingo.ui.community.CommunityViewModel

    class ScheduleFactory(
        private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
                return ScheduleViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }