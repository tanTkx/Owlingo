package com.example.owlingo.component

import com.example.owlingo.database.community.Question

interface ClickListener {
    fun onClick(question: Question)
}