package com.example.owlingo.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.example.owlingo.database.community.Question
import kotlinx.coroutines.launch
import org.json.JSONArray
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.owlingo.database.community.Comment
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditCommentViewModel( commentId: Int, application: Application) : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)
    private val toastMsg = MutableLiveData<String?>()

    private val _comment = MutableLiveData<Comment>()
    val comment: LiveData<Comment>
        get() = _comment

    private val _commentTitle = MutableLiveData<String>()
    val commentTitle : LiveData<String>
        get() = _commentTitle

    private val _commentText = MutableLiveData<String>()
    val commentText : LiveData<String>
        get() = _commentText

    private val _commentId = MutableLiveData<Int>()
    val commentId : LiveData<Int>
        get() = _commentId

    init {
        _commentTitle.value = " "
        _commentText.value = " "
        _commentId.value = commentId
        getComment(commentId)
    }

    fun updateCommentDetail(title:String, text: String) {
        _commentTitle.value = title
        _commentText.value = text
        editComment()
    }

    private fun getComment(commentId: Int){
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/commentDAO.php?commentId=$commentId"
                Log.i("url", urlWithParams)
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, urlWithParams, null,
                    { response ->
                        _comment.value = parseComment(response)
                    },
                    { error ->
                        showToast("$error")
                        Log.e("Connection Error Msg", "$error")
                    }
                )

                requestQueue.add(jsonObjectRequest)
            } catch (e: Exception) {
                showToast("Exception $e")
            }
        }
    }


    private fun editComment() {
        viewModelScope.launch {
            try {
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, "http://10.0.2.2/Owlingo/commentDAO.php",
                    Response.Listener { response ->

                        if (response == "success") {
                            showToast("Comment Successful Updated ")
                        } else if (response == "failure") {
                            showToast("Comment Updated Failed")
                        }else{
                            showToast("Comment Updated Failed")
                            Log.e("Connection Error Msg", response.toString())
                        }},

                    Response.ErrorListener { error ->
                        showToast("Comment Updated Failed")
                        Log.e("Connection Error Msg", "$error")
                    }) {

                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String>? {
                        val data: MutableMap<String, String> = HashMap()

                        data["commentId"] = _commentId.value.toString()
                        data["commentTitle"] = _commentTitle.value.toString()
                        data["commentText"] = _commentText.value.toString()
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
    }

    private fun parseComment(response: JSONObject): Comment {

        return Comment(
            commentId = response.getInt("commentId"),
            questionId = response.getInt("questionId"),
            commentTitle = response.getString("commentTitle"),
            commentText = response.getString("commentText"),
            userId  = response.getInt("userId"),
            commentLike = response.getInt("commentLike"),
            commentDisLike = response.getInt("commentDislike"),
            commentDateTime   = response.getString("commentDateTime"),
        )
    }


    private fun showToast(message: String) {
        toastMsg.value = message
    }

    fun toastShown() {
        toastMsg.value = null
    }

    fun getToastMessage() = toastMsg

}