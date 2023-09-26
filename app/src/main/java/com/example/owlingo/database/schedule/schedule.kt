package com.example.owlingo.database.schedule

data class Schedule(
    var scheduleID: String = " ",
    var userID: String = " ",
    var selectedCours: String = " ",
    val selectedDay: String = " ",
    val selectedTime: String = " ",
)