package com.example.owlingo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.owlingo.database.course.Course

object UserInformation {
    val _userID = MutableLiveData<String>()
    val userID: LiveData<String>
        get() = _userID


    fun setUserID(newUserID: String) {
        _userID.value = newUserID
    }
}
