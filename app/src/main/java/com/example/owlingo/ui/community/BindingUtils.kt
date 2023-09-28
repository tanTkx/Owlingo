package com.example.owlingo.ui.community

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.community.CommentWithUser
import com.example.owlingo.database.community.Question
import com.example.owlingo.ui.UserInformation


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

        @BindingAdapter("commentTitle")
        @JvmStatic
        fun TextView.setCommentTitle(item: CommentWithUser) {
            text = item.commentTitle
        }

        @BindingAdapter("commentText")
        @JvmStatic
        fun TextView.setCommentText(item: CommentWithUser) {
            text = item.commentText
        }

        @BindingAdapter("commentDateTime")
        @JvmStatic
        fun TextView.setCommentDateTime(item: CommentWithUser) {
            text = item.commentDateTime
        }

        @BindingAdapter("commentUserName")
        @JvmStatic
        fun TextView.setCommentUserName(item: CommentWithUser) {
            text = item.username
        }

        @BindingAdapter("email")
        @JvmStatic
        fun Button.setEmail(item: CommentWithUser) {
            text = item.useremail
        }

        @BindingAdapter("dislike")
        @JvmStatic
        fun Button.setDislike(item: CommentWithUser) {
            text = item.commentDisLike.toString()
        }

        @BindingAdapter("like")
        @JvmStatic
        fun Button.setLike(item: CommentWithUser) {
            text = item.commentLike.toString()
        }

        @BindingAdapter("editVisible")
        @JvmStatic
        fun Button.setBtnVisible(item: CommentWithUser) {
            isVisible = (item.userId == UserInformation._userID.value?.toInt() ?: 0)
        }


    }
}