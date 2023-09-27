package com.example.owlingo.ui

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val USER_ID_KEY = "user_id"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    }

    fun setUserId(userId: Int) {
        sharedPreferences.edit().putInt(USER_ID_KEY, userId).apply()
    }

    fun getUserId(): Int? {
        return sharedPreferences.getInt(USER_ID_KEY, -1)
    }
}
