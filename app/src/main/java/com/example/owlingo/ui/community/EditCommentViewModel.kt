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
import com.android.volley.toolbox.StringRequest

class EditCommentViewModel( questionId: Int, application: Application) : ViewModel(){

    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    private val toastMsg = MutableLiveData<String?>()

    private val _commentTitle = MutableLiveData<String>()
    val commentTitle : LiveData<String>
        get() = _commentTitle

    private val _commentText = MutableLiveData<String>()
    val commentText : LiveData<String>
        get() = _commentText

    private val _commentId = MutableLiveData<Int>()
    val commentId: LiveData<Int>
        get() = _commentId

    init {
        _commentTitle.value = " "
        _commentText.value = " "
        _commentId.value = questionId
    }


    private fun editComment( commentId: Int ) {
        viewModelScope.launch {
            try {
                val urlWithParams = "http://10.0.2.2/Owlingo/commentDAO.php"

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.PUT, urlWithParams, null,
                    { response ->
                        showToast("Successfully Edit Comment")
                    },
                    { error ->
                        showToast("$error")
                        Log.e("Connection Error Msg", "$error")
                    }
                )

                requestQueue.add(jsonArrayRequest)
            } catch (e: Exception) {
                showToast("Exception $e")
            }
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