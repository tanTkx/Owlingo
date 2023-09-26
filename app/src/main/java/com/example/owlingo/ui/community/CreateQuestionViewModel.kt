package com.example.owlingo.ui.community

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.course.Course
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date

class CreateQuestionViewModel (userId: Int, application: Application) : ViewModel() {

    private val _questionTitle = MutableLiveData<String>()
    val questionTitle : LiveData<String>
        get() = _questionTitle

    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>>
        get() = _courseList

    private val _questionText = MutableLiveData<String>()
    val questionText : LiveData<String>
        get() = _questionText

    private val _courseId = MutableLiveData<Int>()
    val courseId: LiveData<Int>
        get() = _courseId

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val toastMsg = MutableLiveData<String?>()

    init {
        _questionTitle.value = " "
        _questionText.value = " "
        _courseId.value = 1
        _userId.value = 1
    }

    fun updateQuestionDetail(title:String, text: String) {
        _questionText.value = text
        _questionTitle.value = title
        _courseId.value = 1
        createQuestion()
    }

    private fun createQuestion() {
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, "http://10.0.2.2/Owlingo/questionDAO.php",
                Response.Listener { response ->

                    if (response == "success") {
                        showToast("Question Successful Uploaded ")
                    } else if (response == "failure") {
                        showToast("Question Uploaded Failed")
                    }else{
                        Log.e("Connection Error Msg", response.toString())
                    }},
                Response.ErrorListener { error ->
                    showToast("Question Uploaded Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()

                    data["questionTitle"] = _questionTitle.value.toString()
                    data["questionText"] = _questionText.value.toString()
                    data["userId"] = userId.toString()
                    data["courseId"] = courseId.toString()
                    data["questionDateTime"] = Date().toString()
                    data["commentNo"] = "0"
                    return data
                }
            }
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            Log.e("Error", e.toString())
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