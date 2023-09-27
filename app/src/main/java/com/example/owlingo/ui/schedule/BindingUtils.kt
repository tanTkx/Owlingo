package com.example.owlingo.ui.schedule

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.schedule.Schedule

class BindingUtils {
    companion object {
        @BindingAdapter("scheduleID")
        @JvmStatic
        fun TextView.setScheduleID(item: Schedule) {
            text = item.scheduleID
        }
        @BindingAdapter("scheduleCourse")
        @JvmStatic
        fun TextView.setScheduleCourse(item: Schedule) {
            text = item.selectedCourse
        }
        @BindingAdapter("scheduleDayTime")
        @JvmStatic
        fun TextView.setScheduleDayTime(item: Schedule) {
            text = item.selectedDay + " " +item.selectedStartTime + " - " + item.selectedEndTime
        }




    }
}