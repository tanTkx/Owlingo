package com.example.owlingo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.owlingo.database.community.Question
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class QuestionDBTest {

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
        }
    }
}