package com.example.owlingo.ui.course

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.owlingo.database.course.Course


class BindingUtils {

    companion object {

        @BindingAdapter("courseName")
        @JvmStatic
        fun TextView.setCourseName(item: Course) {
            text = item.course_name
        }

        @BindingAdapter("courseLecture")
        @JvmStatic
        fun TextView.setCourseLecture(item: Course) {
            text = item.course_lecture
        }

        @BindingAdapter("courseDetail")
        @JvmStatic
        fun TextView.setCourseDetail(item: Course) {
            text = item.course_detail
        }

        @BindingAdapter("courseFee")
        @JvmStatic
        fun TextView.setCourseFee(item: Course) {
            text = item.course_fee.toString()
        }


    }

}