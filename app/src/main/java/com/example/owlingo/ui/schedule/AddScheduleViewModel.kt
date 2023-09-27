package com.example.owlingo.ui.schedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




class AddScheduleViewModel (
    application: Application
) : ViewModel() {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)
    private val toastMsg = MutableLiveData<String?>()

    private val _selectedCourse = MutableLiveData<String>()
    val selectedCourse : LiveData<String>
        get() = _selectedCourse

    private val _selectedDay = MutableLiveData<String>()
    val selectedDay : LiveData<String>
        get() = _selectedDay

    private val _selectedStartTime = MutableLiveData<String>()
    val selectedStartTime: LiveData<String>
        get() = _selectedStartTime

    private val _selectedEndTime = MutableLiveData<String>()
    val selectedEndTime: LiveData<String>
        get() = _selectedEndTime

    init {
        _selectedCourse.value = " "
        _selectedDay.value = " "
        _selectedStartTime.value = " "
        _selectedEndTime.value = " "
    }

    fun updateScheduleDetail(selectedCourse:String, selectedDay: String, selectedStartTime: String, selectedEndTime: String) {
        _selectedCourse.value = selectedCourse
        _selectedDay.value = selectedDay
        _selectedStartTime.value = selectedStartTime
        _selectedEndTime.value = selectedEndTime
        createSchedule()
    }

    private fun createSchedule() {
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, "http://10.0.2.2/Owlingo/scheduleDAO.php",
                Response.Listener { response ->

                    if (response == "success") {
                        showToast("Comment Successful Uploaded ")
                    } else if (response == "failure") {
                        showToast("Comment Uploaded Failed")
                    }else{
                        Log.e("Connection Error Msg", response.toString())
                    }},

                Response.ErrorListener { error ->
                    showToast("Comment Uploaded Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()

                    data["submitInsert"] = "Insert"
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

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg
}