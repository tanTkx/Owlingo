package com.example.owlingo.database.course

data class Course(

    val course_id: Int=0,
    val course_name: String="",
    var course_detail: String="",
    var course_lecture: String="",
    var course_fee: Int=0,
    val course_schedule: String="",


    )