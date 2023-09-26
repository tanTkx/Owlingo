package com.example.owlingo.ui.community

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Comment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateCommentViewModel (
    userId: Int,
    questionId: Int,
    application: Application) : ViewModel() {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)
    private val toastMsg = MutableLiveData<String?>()

    private val _commentTitle = MutableLiveData<String>()
    val commentTitle : LiveData<String>
        get() = _commentTitle

    private val _commentText = MutableLiveData<String>()
    val commentText : LiveData<String>
        get() = _commentText

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    private val _questionId = MutableLiveData<Int>()
    val questionId: LiveData<Int>
        get() = _questionId

    init {
        _commentTitle.value = " "
        _commentText.value = " "
        _questionId.value = questionId
        _userId.value = 1
    }

    fun updateCommentDetail(title:String, text: String) {
        _commentText.value = text
        _commentTitle.value = title
        createComment()
    }

    private fun createComment() {
        try {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, "http://10.0.2.2/Owlingo/commentDAO.php",
                Response.Listener { response ->

                    if (response == "success") {
                        showToast("Comment Successful Uploaded ")
                    } else if (response == "failure") {
                        showToast("Comment Uploaded Failed")
                    }else{
                        Log.e("Connection Error Msg", response.toString())
                    }},

                Response.ErrorListener { error ->
                    showToast("Comment Uploaded Failed")
                    Log.e("Connection Error Msg", "$error")
                }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    val dateFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())

                    data["commentTitle"] = _commentTitle.value.toString()
                    data["commentText"] = _commentText.value.toString()
                    data["userId"] = userId.value.toString()
                    data["questionId"] = _questionId.value.toString()
                    data["commentDateTime"] = dateFormat.format(Date()).toString()
                    data["commentLike"] = "0"
                    data["commentDisLike"] = "0"

                    return data
                }
            }
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            Log.e("Error", e.toString())
            showToast("Exception $e")
        }
    }

    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg
}
