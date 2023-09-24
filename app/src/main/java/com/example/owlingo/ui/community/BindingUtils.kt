package com.example.owlingo.ui.community

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.owlingo.database.community.Question


class BindingUtils {

    companion object {
        @BindingAdapter("questionTitle")
        @JvmStatic
        fun TextView.setQuestionTitle(item: Question) {
            text = item.questionTitle
        }

        @BindingAdapter("questionText")
        @JvmStatic
        fun TextView.setQuestionText(item: Question) {
            text = item.questionText
        }

        @BindingAdapter("commentNo")
        @JvmStatic
        fun Button.setCommentNo(item: Question) {
            text = item.commentNo.toString()
        }
    }

//    @BindingAdapter("sleepImage")
//    fun ImageView.setSleepImage(item: SleepNight) {
//        setImageResource(when (item.sleepQuality) {
//            0 -> R.drawable.ic_sleep_0
//            1 -> R.drawable.ic_sleep_1
//            2 -> R.drawable.ic_sleep_2
//            3 -> R.drawable.ic_sleep_3
//            4 -> R.drawable.ic_sleep_4
//            5 -> R.drawable.ic_sleep_5
//            else -> R.drawable.ic_sleep_active
//        })
//    }
}