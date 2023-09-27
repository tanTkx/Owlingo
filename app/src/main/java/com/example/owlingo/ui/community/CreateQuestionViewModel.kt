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
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.course.Course
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateQuestionViewModel (userId: Int, application: Application) : ViewModel() {

    private val _questionTitle = MutableLiveData<String>()
    val questionTitle : LiveData<String>
        get() = _questionTitle

    private val _courseList = MutableLiveData<List<String>>()
    val courseList: LiveData<List<String>>
        get() = _courseList

    private val _questionText = MutableLiveData<String>()
    val questionText : LiveData<String>
        get() = _questionText

    val _courseName = MutableLiveData<String>()
    val courseName: LiveData<String>
        get() = _courseName

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val toastMsg = MutableLiveData<String?>()

    init {
        getRegisteredCourse(userId)
        _questionTitle.value = " "
        _questionText.value = " "
        _courseName.value = " "
        _userId.value = userId
    }

    private fun getRegisteredCourse(userId: Int){
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/courseRegDAO.php?userId=$userId"

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _courseList.postValue(parseCourses(response))
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

    fun updateQuestionDetail(title:String, text: String) {
        _questionText.value = text
        _questionTitle.value = title
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
                        showToast("Question Uploaded Failed")
                        Log.e("Connection Error Msg", response.toString())
                    }},
                Response.ErrorListener { error ->
                    showToast("Question Uploaded Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    val dateFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())

                    data["questionTitle"] = _questionTitle.value.toString()
                    data["questionText"] = _questionText.value.toString()
                    data["userId"] = userId.toString()
                    data["courseName"] = _courseName.value.toString()
                    data["questionDateTime"] = dateFormat.format(Date()).toString()
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

    private fun parseCourses(response: JSONArray): MutableList<String> {
        val courses = mutableListOf<String>()
        for (i in 0 until response.length()) {
            val jsonObject = response.getJSONObject(i)
            val course = jsonObject.getString("course_name")
            courses.add(course)
        }
        return courses
    }

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg
}