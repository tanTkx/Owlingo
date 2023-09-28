package com.example.owlingo.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONArray
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.example.owlingo.database.schedule.Schedule


class ScheduleViewModel( application: Application) : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val _scheduleList = MutableLiveData<List<Schedule>>()
    val scheduleList: LiveData<List<Schedule>>
        get() = _scheduleList

    private val toastMsg = MutableLiveData<String?>()

    init {
        initializeScheduleList()
    }

    fun refresh(){
        initializeScheduleList()
    }

    private fun initializeScheduleList() {
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/scheduleDao.php"

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _scheduleList.postValue(parseSchedule(response))
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

        Log.i("ScheduleList", _scheduleList.toString())
    }

    fun deleteSchedule(scheduleID: Int){
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, "http://10.0.2.2/Owlingo/scheduleDAO.php?submitDelete=1&scheduleID=$scheduleID",
                Response.Listener { response ->

                    if (response == "success") {
                        showToast("Schedule Successful Deleted ")
                    } else if (response == "failure") {
                        showToast("Schedule Delete Failed")
                    }else{
                        showToast("Comment Delete Failed")
                        Log.e("Connection Error Msg", response.toString())
                    }},

                Response.ErrorListener { error ->
                    showToast("Schedule Uploaded Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            Log.e("Error", e.toString())
            showToast("Exception $e")
        }
    }

    private fun parseSchedule(response: JSONArray): MutableList<Schedule> {
        val schedules = mutableListOf<Schedule>()
        for (i in 0 until response.length()) {
            val jsonObject = response.getJSONObject(i)
            val schedule = Schedule(
            scheduleID = jsonObject.getString("scheduleID"),
            selectedCourse = jsonObject.getString("selectedCourse"),
            selectedDay = jsonObject.getString("selectedDay"),
            selectedStartTime   = jsonObject.getString("selectedStartTime"),
            selectedEndTime   = jsonObject.getString("selectedEndTime"),
            )
            schedules.add(schedule)
        }
        return schedules
    }

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg

}