package com.example.owlingo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private val URL: String = "http://10.0.2.2/Owlingo_php/login.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        etEmail = view.findViewById(R.id.email_edit)
        etPassword = view.findViewById(R.id.password_edit)
        return view
    }

    fun login(view: View?) {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response.trim() == "success") {
                        // Navigate to the SuccessActivity after successful login
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else if (response.trim() == "failure") {
                        Toast.makeText(
                            requireActivity(),
                            "Invalid Login Id/Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        requireActivity(),
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email
                    data["password"] = password
                    return data
                }
            }

            val requestQueue = Volley.newRequestQueue(requireActivity())
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(requireActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
