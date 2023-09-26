package com.example.owlingo.database.community


data class Comment(


    var commentId: Int = 0,

    var questionId: Int = 0,

    val commentTitle: String = "",

    val commentText: String = "",

    val commentLike: Int = 0,

    val commentDisLike: Int = 0,

    var commentDateTime: String = "",

    var userId: Int = 0,

    )