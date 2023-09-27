package com.example.owlingo.database.schedule

data class Schedule(
    var scheduleID: String = " ",
    var selectedCourse: String = " ",
    val selectedDay: String = " ",
    val selectedStartTime: String = " ",
    val selectedEndTime: String = " ",
)