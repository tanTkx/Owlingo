package com.example.owlingo.ui.community

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Question
import org.json.JSONObject

class QuestionViewModel(questionId: Int, application: Application) : ViewModel() {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val toastMsg = MutableLiveData<String?>()

    init {

        try {
            val urlWithParams = "http://10.0.2.2/Owlingo/questionDAO.php?questionId=$questionId"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, urlWithParams, null,
                { response ->
                    _question.value = parseQuestion(response)
                },
                { error ->
                    showToast("$error")
                    Log.e("Connection Error Msg", "$error")
                }
            )

            requestQueue.add(jsonObjectRequest)
        } catch (e: Exception) {
            showToast("Exception $e")
        }
    }

    private fun parseQuestion(response: JSONObject): Question {

        return Question(
            questionId = response.getInt("questionId"),
            questionTitle = response.getString("questionTitle"),
            questionText = response.getString("questionText"),
            userId  = response.getInt("userId"),
            questionDateTime = response.getString("questionDateTime"),
            commentNo = response.getInt("commentNo"),
        )
    }

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg


}