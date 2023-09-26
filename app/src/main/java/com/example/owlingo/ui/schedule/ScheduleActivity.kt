package com.example.owlingo.ui.schedule

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.R
import com.example.owlingo.database.schedule.Schedule
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ScheduleActivity : AppCompatActivity() {

    private lateinit var spinnerCourse: Spinner
    private lateinit var spinnerDay: Spinner
    private lateinit var spinnerTime: Spinner
    private lateinit var btnCreate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_schedule)

        // Initialize views
        spinnerCourse = findViewById(R.id.spinnerCourse)
        spinnerDay = findViewById(R.id.spinnerDay)
        spinnerTime = findViewById(R.id.spinnerTime)
        btnCreate = findViewById(R.id.btnCreate)

        // Populate spinners with data (You need to fetch data from your PHP server)
        val courseNames = arrayOf("Course A", "Course B", "Course C")
        val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val times = arrayOf("8:00 AM", "10:00 AM", "2:00 PM", "4:00 PM")

        val courseAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseNames)
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, times)

        spinnerCourse.adapter = courseAdapter
        spinnerDay.adapter = dayAdapter
        spinnerTime.adapter = timeAdapter

        btnCreate.setOnClickListener {
            // Get selected values from spinners
            val selectedCourse = spinnerCourse.selectedItem.toString()
            val selectedDay = spinnerDay.selectedItem.toString()
            val selectedTime = spinnerTime.selectedItem.toString()

            // Create a Schedule object
            val schedule = Schedule(selectedCourse, selectedDay, selectedTime)
            val requestQueue: RequestQueue = Volley.newRequestQueue(application)
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, "http://10.0.2.2/Owlingo/commentDAO.php",
                Response.Listener { response ->

                    if (response == "success") {
                        //toast
                    } else if (response == "failure") {
                        //toast
                    }else{
                        Log.e("Connection Error Msg", response.toString())
                    }},

                Response.ErrorListener { error ->
                //toast

                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["fieldName"] = schedule.selectedTime
                    return data
                }
            }
            requestQueue.add(stringRequest)
        }
    }
}
