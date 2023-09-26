package com.example.owlingo.ui.community

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateCommentFactory(
    private val userId: Int,
    private val questionId: Int,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCommentViewModel::class.java)) {
            return CreateCommentViewModel(userId, questionId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}