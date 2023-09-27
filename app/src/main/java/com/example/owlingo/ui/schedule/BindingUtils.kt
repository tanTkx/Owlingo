package com.example.owlingo.ui.schedule

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.schedule.Schedule

class BindingUtils {
    companion object {
        @BindingAdapter("scheduleText")
        @JvmStatic
        fun TextView.setScheduleText(item: Schedule) {
            text = item.selectedCourse
        }

    }
}