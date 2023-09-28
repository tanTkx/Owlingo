package com.example.owlingo.ui.schedule

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
import com.example.owlingo.database.schedule.Schedule
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class  EditScheduleViewModel( scheduleId: Int, application: Application) : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)
    private val toastMsg = MutableLiveData<String?>()
    private val _courseList = MutableLiveData<List<String>>()

    private val _schedule = MutableLiveData<Schedule>()
    val schedule: LiveData<Schedule>
        get() = _schedule

    val courseList: LiveData<List<String>>
        get() = _courseList
    val _selectedCourse = MutableLiveData<String>()


    private val _scheduleID = MutableLiveData<Int>()
    val scheduleID: LiveData<Int>
        get() = _scheduleID

    val selectedCourse : LiveData<String>
        get() = _selectedCourse

    val _selectedDay = MutableLiveData<String>()
    val selectedDay : LiveData<String>
        get() = _selectedDay

    val _selectedStartTime = MutableLiveData<String>()
    val selectedStartTime: LiveData<String>
        get() = _selectedStartTime

    val _selectedEndTime = MutableLiveData<String>()
    val selectedEndTime: LiveData<String>
        get() = _selectedEndTime

    init {
        _selectedCourse.value = " "
        _selectedDay.value = " "
        _selectedStartTime.value = " "
        _selectedEndTime.value = " "
        _scheduleID.value = scheduleId
        getSchedule(scheduleId)
        getCourse()
    }

    fun updateScheduleDetail(selectedCourse:String, selectedDay: String, selectedStartTime: String, selectedEndTime: String) {
        _selectedCourse.value = selectedCourse
        _selectedDay.value = selectedDay
        _selectedStartTime.value = selectedStartTime
        _selectedEndTime.value = selectedEndTime
        updateSchedule()
    }

    private fun getCourse(){
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/courseAllDAO.php"

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

    private fun getSchedule(scheduleID: Int){
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/scheduleDAO.php?scheduleID=$scheduleID"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _schedule.value = parseSchedule(response)
                        if(_schedule.value!= null){
                            _selectedCourse.value = _schedule.value!!.selectedCourse
                            _selectedDay.value = _schedule.value!!.selectedDay
                            _selectedStartTime.value = _schedule.value!!.selectedStartTime
                            _selectedEndTime.value = _schedule.value!!.selectedEndTime
                        }

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


    private fun updateSchedule() {
        viewModelScope.launch {
            try {
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, "http://10.0.2.2/Owlingo/scheduleDAO.php",
                    Response.Listener { response ->

                        if (response == "success") {
                            showToast("Schedule Successful Updated ")
                        } else if (response == "failure") {
                            showToast("Schedule Updated Failed")
                        }else{
                            showToast("Schedule Updated Failed")
                            Log.e("Connection Error Msg", response.toString())
                        }},

                    Response.ErrorListener { error ->
                        showToast("Schedule Updated Failed")
                        Log.e("Connection Error Msg", "$error")
                    }) {

                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String>? {
                        val data: MutableMap<String, String> = HashMap()

                        data["submitEdit"] = "Edit"
                        data["scheduleID"] = _scheduleID.value.toString()
                        data["selectedCourse"] = _selectedCourse.value.toString()
                        data["selectedDay"] = _selectedDay.value.toString()
                        data["selectedStartTime"] = _selectedStartTime.value.toString()
                        data["selectedEndTime"] = _selectedEndTime.value.toString()

                        return data
                    }
                }
                requestQueue.add(stringRequest)
            } catch (e: Exception) {
                Log.e("Error", e.toString())
                showToast("Exception $e")
            }
        }
    }
    private fun parseSchedule(response: JSONObject): Schedule {

        return Schedule(
            scheduleID = response.getString("scheduleID"),
            selectedCourse = response.getString("selectedCourse"),
            selectedDay = response.getString("selectedDay"),
            selectedStartTime = response.getString("selectedStartTime"),
            selectedEndTime   = response.getString("selectedEndTime"),
        )
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