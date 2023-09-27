package com.example.owlingo.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.MainActivity
import com.example.owlingo.R
import com.example.owlingo.ui.UserInformation
import org.json.JSONException
import org.json.JSONObject

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

        val button = view.findViewById<Button>(R.id.login_btn)

        // Set an OnClickListener to handle button click
        button.setOnClickListener {
            // Navigate to another fragment (you'll need to replace YourNextFragment with the actual fragment)
            login(view)
        }

        val backButton = view.findViewById<ImageButton>(R.id.back_btn)

        // Set an OnClickListener to handle button click
        backButton.setOnClickListener {
            // Navigate to another fragment (you'll need to replace YourNextFragment with the actual fragment)
            findNavController().navigate(R.id.action_loginFragment_to_StartFragment)
        }

        val createButton = view.findViewById<TextView>(R.id.loginHere_btn)

        // Set an OnClickListener to handle button click
        createButton.setOnClickListener {
            // Navigate to another fragment (you'll need to replace YourNextFragment with the actual fragment)
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return view
    }

    private fun login(view: View?) {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    handleLoginResponse(response)
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

    private fun handleLoginResponse(response: String) {
        try {
            val jsonResponse = JSONObject(response)
            val userID = jsonResponse.getString("userID")
            when (val status = jsonResponse.getString("status")) {
                "success" -> {
                    Toast.makeText(
                        requireActivity(),
                        "Success",
                        Toast.LENGTH_SHORT
                    ).show()

                    UserInformation.userID = userID

                    findNavController().navigate(R.id.action_loginFragment_to_accountFragment)
                }
                "failure" -> {
                    Toast.makeText(
                        requireActivity(),
                        "Invalid Login Id/Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        requireActivity(),
                        "Unknown status: $status",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(
                requireActivity(),
                "Error parsing response",
                Toast.LENGTH_SHORT
            ).show()
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
