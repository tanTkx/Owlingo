package com.example.owlingo.ui.course

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.course.Course
import com.example.owlingo.ui.UserInformation
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class UserDetailCourseViewModel(courseId: Int, application: Application)  : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val _course = MutableLiveData<Course>()
    val course: LiveData<Course>
        get() = _course

    var _isVisible: String = "visible"

    private val toastMsg = MutableLiveData<String?>()

    init {
        initializeCourse(courseId)
    }

    private fun initializeCourse(courseId: Int) {

        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/getCourse.php?courseId=$courseId"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _course.value = parseCourse(response)
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

        val userId: Int = UserInformation.userID?.toInt() ?: 0
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.GET, "http://10.0.2.2/Owlingo/checkRegistered.php?userId=$userId&courseId=$courseId",
                Response.Listener { response ->

                    if (response == "visible") {
                       _isVisible = response
                    } else if (response == "invisible") {
                        _isVisible = response
                    }else{
                        Log.e("Connection Error Msg", response.toString())
                    }},

                Response.ErrorListener { error ->
                    Log.e("Connection Error Msg", "$error")
                }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            Log.e("Error", e.toString())
            showToast("Exception $e")
        }
    }
    private fun parseCourse(response: JSONObject): Course {

        return Course(
            course_id = response.getInt("course_id") ,
            course_name = response.getString("course_name"),
            course_detail = response.getString("course_detail"),
            course_lecture = response.getString("course_lecture"),
            course_fee = response.getInt("course_fee"),
//            course_schedule = response.getString("course_schedule").orEmpty()
        )
    }

    fun unlockCourse(){
        try {

            val courseId: Int = _course.value?.course_id?: 0
            val userId: Int = UserInformation.userID?.toInt() ?: 0

            val urlWithParams = "http://10.0.2.2/Owlingo/unlockCourseDAO.php?courseId=$courseId&userId=$userId"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, urlWithParams, null,
                { response ->
                    showToast(response.toString())
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

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg


}