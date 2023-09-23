package com.example.owlingo.database.community

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Update

@Dao
interface CommentDatabaseDao {
    @Insert
    suspend fun insert(comment: Comment)

    @Update
    suspend fun update(comment: Comment)

    @Query("SELECT * from comment WHERE question_id = :key")
    fun get(key: Long): LiveData<List<Comment>>

    @Query("DELETE FROM comment WHERE commentId = :key")
    suspend fun removeComment(key: Long): Int

    @Query("DELETE FROM sub_comment WHERE comment_id = :key")
    suspend fun removeSubCommentForComment(key: Long)

    @Query("SELECT * FROM comment WHERE commentId = :key")
    suspend fun getComment(key: Long): Comment?

}
