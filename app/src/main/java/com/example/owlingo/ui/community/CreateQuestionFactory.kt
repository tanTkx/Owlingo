package com.example.owlingo.ui.community

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateQuestionFactory(
    private val userId: Int,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateQuestionViewModel::class.java)) {
            return CreateQuestionViewModel(userId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}