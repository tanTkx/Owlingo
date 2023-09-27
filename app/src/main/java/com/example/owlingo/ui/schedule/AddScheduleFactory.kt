package com.example.owlingo.ui.schedule

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.owlingo.ui.schedule.AddScheduleViewModel


class AddScheduleFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddScheduleViewModel::class.java)) {
            return AddScheduleViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}