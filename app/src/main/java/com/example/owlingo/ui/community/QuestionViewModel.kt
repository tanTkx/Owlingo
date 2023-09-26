package com.example.owlingo.ui.community

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.community.Question
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date

class QuestionViewModel(questionId: Int, application: Application) : ViewModel() {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>>
        get() = _commentList

    private val toastMsg = MutableLiveData<String?>()

    init {
        refresh(questionId)
    }

    fun refresh(questionId: Int){

        viewModelScope.launch {
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

            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/commentDAO.php?questionId=$questionId"

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _commentList.postValue(parseComments(response))
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

    private fun parseComments(response: JSONArray): MutableList<Comment> {
        val comments = mutableListOf<Comment>()
        for (i in 0 until response.length()) {
            val jsonObject = response.getJSONObject(i)
            val comment = Comment(
                commentId = jsonObject.getInt("commentId"),
                questionId = jsonObject.getInt("questionId"),
                commentTitle = jsonObject.getString("commentTitle"),
                commentText = jsonObject.getString("commentText"),
                userId  = jsonObject.getInt("userId"),
                commentLike = jsonObject.getInt("commentLike"),
                commentDisLike = jsonObject.getInt("commentDislike"),
                commentDateTime   = jsonObject.getString("commentDateTime"),
            )
            comments.add(comment)
        }
        return comments
    }

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg


}