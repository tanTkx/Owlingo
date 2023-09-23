package com.example.owlingo.database.community

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "sub_comment")
data class SubComment(

    @PrimaryKey(autoGenerate = true)
    var subCommentId: Long = 0L,

    @ColumnInfo(name = "comment_id")
    var commentId: Long = 0L,

    @ColumnInfo(name = "subComment_text", typeAffinity = ColumnInfo.TEXT)
    val subCommentText: String,

    @ColumnInfo(name = "subComment_dateTime")
    var subCommentDateTime: String,

    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,

    )