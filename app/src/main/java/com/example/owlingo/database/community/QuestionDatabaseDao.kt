package com.example.owlingo.database.community

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {
    @Insert
    suspend fun insert(question: Question)

    @Update
    suspend fun update(question: Question)

    @Query("SELECT * FROM question WHERE course_id IN (:courseIdList)")
    suspend fun getAll(courseIdList: List<Long>): LiveData<List<Question>>

    @Query("DELETE FROM question WHERE questionId = :key")
    suspend fun delete(key: Long)

    @Query("SELECT * FROM question WHERE questionId = :key")
    suspend fun getQuestion(key: Long): QuestionWithComments?
}

data class QuestionWithComments(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "question_id"
    )
    val comments: List<Comment>
)