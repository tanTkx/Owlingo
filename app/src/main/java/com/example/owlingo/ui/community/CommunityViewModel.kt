package com.example.owlingo.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Question
import kotlinx.coroutines.launch
import org.json.JSONArray
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.example.owlingo.ui.UserInformation

class CommunityViewModel( application: Application) : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val _questionList = MutableLiveData<List<Question>>()
    val questionList: LiveData<List<Question>>
        get() = _questionList

    private val toastMsg = MutableLiveData<String?>()

    init {
        val userId: Int = UserInformation._userID.value?.toInt() ?: 0
        initializeQuestionList(userId)
    }

    fun refreshQuestionList(userId: Int, searchText: String? = null) {
        if(searchText!=null){
            searchQuestionList(userId, searchText)
        }
        else {
            initializeQuestionList(userId)
        }
    }

    private fun initializeQuestionList( userId: Int) {
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/questionDAO.php?userId=$userId"

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _questionList.postValue(parseQuestions(response))
                    },
                    { error ->
                        showToast("$error")
                        Log.e("Connection Error Msg", "$error")
                    }
                )

                requestQueue.add(jsonArrayRequest)
            } catch (e: Exception) {
                showToast("Exception $e")
            }
        }
    }

    private fun searchQuestionList( userId: Int, searchText: String) {
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/questionDAO.php?userId=$userId&searchText=$searchText"
                Log.e("url", urlWithParams)
                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->

                        _questionList.postValue(parseQuestions(response))
                    },
                    { error ->
                        showToast("$error")
                        Log.e("Connection Error Msg", "$error")
                    }
                )

                requestQueue.add(jsonArrayRequest)
            } catch (e: Exception) {
                showToast("Exception $e")
            }
        }
    }

    private fun parseQuestions(response: JSONArray): MutableList<Question> {
        val questions = mutableListOf<Question>()
        for (i in 0 until response.length()) {
            val jsonObject = response.getJSONObject(i)
            val question = Question(
                questionId = jsonObject.getInt("questionId"),
                questionTitle = jsonObject.getString("questionTitle"),
                questionText = jsonObject.getString("questionText"),
                userId  = jsonObject.getInt("userId"),
                questionDateTime   = jsonObject.getString("questionDateTime"),
                commentNo   = jsonObject.getInt("commentNo"),
            )
            questions.add(question)
        }
        return questions
    }

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg

}