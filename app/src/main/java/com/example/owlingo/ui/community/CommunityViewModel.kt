package com.example.owlingo.ui.community

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.community.QuestionDatabase
import com.example.owlingo.database.community.QuestionDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommunityViewModel(
    val database: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    var questions: LiveData<List<Question>> = MutableLiveData<List<Question>>()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            questions = database.getAll(listOf(0L))
        }
    }

     fun getQuestion() {
//        viewModelScope.launch {
//            questionList = getAll()
//        }
    }

//    private suspend fun getAll(): List<Question> {
//        val list = database.getAll();
//        if(list.isEmpty()){
//            return []
//        }
//
//        return
//    }




}