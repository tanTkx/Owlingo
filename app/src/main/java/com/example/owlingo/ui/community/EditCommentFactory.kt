package com.example.owlingo.ui.community

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditCommentFactory(
    private val commentId: Int,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditCommentViewModel::class.java)) {
            return EditCommentViewModel(commentId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}