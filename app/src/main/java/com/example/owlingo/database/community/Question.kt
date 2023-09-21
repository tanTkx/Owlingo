package com.example.owlingo.database.community

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "question")
data class Question(

    @PrimaryKey(autoGenerate = true)
    var questionId: Long = 0L,

    @ColumnInfo(name = "question_title")
    val questionTitle: String = " ",

    @ColumnInfo(name = "question_text", typeAffinity = ColumnInfo.TEXT)
    val questionText: String = " ",

    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,

    @ColumnInfo(name = "course_id")
    var courseId: Long = 0L,

    @ColumnInfo(name = "question_dateTime")
    var questionDateTime: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "comment_no")
    var commentNo: Int = 0,

    )