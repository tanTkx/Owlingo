package com.example.owlingo.database.community

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {
    @Insert
    suspend fun insert(question: Question)

    @Update
    suspend fun update(question: Question)

    @Query("SELECT * FROM question WHERE course_id IN (:courseIdList)")
    fun getAll(courseIdList: List<Long>): LiveData<List<Question>>

    @Query("DELETE FROM question WHERE questionId = :key")
    suspend fun delete(key: Long)

    @Query("SELECT * FROM question WHERE questionId = :key")
    suspend fun getQuestion(key: Unit): Question?
}
