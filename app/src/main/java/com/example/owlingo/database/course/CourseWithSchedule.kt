package com.example.owlingo.database.course

data class CourseWithSchedule (

    val course_id: Int=0,
    val course_name: String="",
    var course_detail: String="",
    var course_lecture: String="",
    var course_fee: Int=0,
    val selectedDay: String="",
    val selectedStartTime: String="",
    val selectedEndTime: String="",

)