package com.example.owlingo

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.owlingo.database.community.QuestionDatabase
import com.example.owlingo.database.community.Question
import com.example.owlingo.database.community.QuestionDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class QuestionDBTest {

    private lateinit var questionDao: QuestionDatabaseDao
    private lateinit var db: QuestionDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, QuestionDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        questionDao = db.questionDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::db.isInitialized) {
            db.close()
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetQuestion() {
        runBlocking {
            val newQuestion: Question = Question(
                questionTitle = "Sample Title",
                questionText = "Sample Text",
                userId = 1,
                courseId = 2,
                questionDateTime = "2023-9-22",
            )
            val questionId = questionDao.insert(newQuestion)
            val retrievedQuestion = questionDao.getQuestion(questionId)

            // Add your assertions here to verify the correctness of the retrievedQuestion.
            // For example:
            Assert.assertEquals(newQuestion.questionTitle, retrievedQuestion?.questionTitle)
            Assert.assertEquals(newQuestion.questionText, retrievedQuestion?.questionText)
            // Add more assertions as needed.
        }
    }
}