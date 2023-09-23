package com.example.owlingo.database.community

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "comment")
data class Comment(

    @PrimaryKey(autoGenerate = true)
    var commentId: Long = 0L,

    @ColumnInfo(name = "question_id")
    var questionId: Long = 0L,

    @ColumnInfo(name = "comment_title")
    val commentTitle: String,

    @ColumnInfo(name = "comment_text", typeAffinity = ColumnInfo.TEXT)
    val commentText: String,

    @ColumnInfo(name = "comment_like")
    val commentLike: Int = 0,

    @ColumnInfo(name = "comment_disLike")
    val commentDisLike: Int = 0,

    @ColumnInfo(name = "comment_dateTime")
    var commentDateTime: String,

    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,

    )