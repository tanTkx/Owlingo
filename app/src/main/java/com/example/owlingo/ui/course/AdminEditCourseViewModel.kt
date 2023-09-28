package com.example.owlingo.ui.course

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
import com.example.owlingo.database.course.Course
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdminEditCourseViewModel (courseId: Int, application: Application) : ViewModel() {

    private val _course = MutableLiveData<Course>()
    val course: LiveData<Course>
        get() = _course


    private val _courseName = MutableLiveData<String>()
    val courseName: LiveData<String>
        get() = _courseName

    private val _courseDetail = MutableLiveData<String>()
    val courseDetail : LiveData<String>
        get() = _courseDetail

    private val _courseLecture = MutableLiveData<String>()
    val courseLecture: LiveData<String>
        get() = _courseLecture

    private val _courseFee = MutableLiveData<Int>()
    val courseFee: LiveData<Int>
        get() = _courseFee


    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val toastMsg = MutableLiveData<String?>()

    init {
        getCourse(courseId)
        _courseName.value = " "
        _courseDetail.value = " "
        _courseLecture.value = " "
        _courseFee.value = 0
    }

    fun editCourse(courseName:String, courseDetail: String, courseLecture:String, courseFee:Int) {
        _courseName.value = courseName
        _courseDetail.value = courseDetail
        _courseLecture.value = courseLecture
        _courseFee.value = courseFee
        editCourse()
    }

    private fun getCourse(courseId: Int){
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/getCourse.php?courseId=$courseId"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        Log.i("res", response.toString())
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
    }

    private fun editCourse() {
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, "http://10.0.2.2/Owlingo/courseEditDAO.php",
                Response.Listener { response ->

                    if (response == "success") {
                        showToast("Course Successful Edited ")
                    } else if (response == "failure") {
                        showToast("Course Unsuccessful Edited")
                    }else{
                        showToast("Course Edit Failed")
                        Log.e("Connection Error Msg", response.toString())
                    }},
                Response.ErrorListener { error ->
                    showToast("Course Edit Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()

                    data["course_id"] = _course.value?.course_id.toString()
                    data["course_name"] = _courseName.value.toString()
                    data["course_detail"] = _courseDetail.value.toString()
                    data["course_lecture"] = _courseLecture.value.toString()
                    data["course_fee"]  = _courseFee.value.toString()

                    return data
                }
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
            course_fee = response.getInt("course_fee")
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