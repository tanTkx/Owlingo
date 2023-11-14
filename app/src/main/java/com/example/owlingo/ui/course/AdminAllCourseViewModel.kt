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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.course.Course
import kotlinx.coroutines.launch
import org.json.JSONArray

class AdminAllCourseViewModel(application: Application)  : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    //for recycle view adapter
    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>>
        get() = _courseList

    private val toastMsg = MutableLiveData<String?>()

    init {
        initializeCourseList()
    }

    fun refreshCourseList(){
        initializeCourseList()
    }

    //Initialize recycle view data, get data from php database
    //coroutine -> ui thread and network thread
    private fun initializeCourseList() {
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/courseAllDAO.php" //php api

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _courseList.postValue(parseCourses(response)) //echo as json
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

    fun deleteCourse(courseId:Int ){
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.GET, "http://10.0.2.2/Owlingo/courseDeleteDAO.php?course_id=$courseId",
                Response.Listener { response ->

                    if (response == "success") {
                        showToast("Course Successful Deleted ")
                    } else if (response == "failure") {
                        showToast("Course Delete Failed")
                    }else{
                        showToast("Course Delete Failed")
                        Log.e("Connection Error Msg", response.toString())
                    }},

                Response.ErrorListener { error ->
                    showToast("Course Delete Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            Log.e("Error", e.toString())
            showToast("Exception $e")
        }
    }

    private fun parseCourses(response: JSONArray): MutableList<Course> {
        val courses = mutableListOf<Course>()
        for (i in 0 until response.length()) {
            val jsonObject = response.getJSONObject(i)
            val course = Course(
                course_id = jsonObject.getInt("course_id") ,
                course_name = jsonObject.getString("course_name"),
                course_detail = jsonObject.getString("course_detail"),
                course_lecture = jsonObject.getString("course_lecture"),
                course_fee = jsonObject.getInt("course_fee"),
//                course_schedule = jsonObject.getString("course_schedule")

            )
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