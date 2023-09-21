package com.example.owlingo.database.community

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Update

@Dao
interface SubCommentDatabaseDao {
    @Insert
    suspend fun insert(subComments: SubComment)

    @Update
    suspend fun update(subComments: SubComment)

    @Query("SELECT * FROM sub_comment WHERE comment_id = :key")
    suspend fun getAll(key: Long): List<Question>?

    @Query("DELETE FROM sub_comment WHERE subCommentId = :key")
    suspend fun delete(key: Long)

}
