package com.example.owlingo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignupFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etCourseLevel: Spinner
    lateinit var btnRegister: Button
    lateinit var spinner: Spinner

    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var courseLevel: String? = ""

    private val URL: String = "http://10.0.2.2/Owlingo_php/register.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.signup_fragment, container, false)

        // Find the Spinner within the fragment's layout
        spinner = rootView.findViewById(R.id.spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.CoursesLevel,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        etName = rootView.findViewById(R.id.name_edit)
        etEmail = rootView.findViewById(R.id.email_edit)
        etPassword = rootView.findViewById(R.id.password_edit)
        etCourseLevel = rootView.findViewById(R.id.spinner)
        btnRegister = rootView.findViewById(R.id.register_btn)

        btnRegister.setOnClickListener {
            save()
        }

        return rootView
    }

    private fun save() {
        name = etName.text.toString().trim()
        email = etEmail.text.toString().trim()
        password = etPassword.text.toString().trim()
        courseLevel = etCourseLevel.selectedItem.toString().trim()

        if (name != "" && email != "" && password != "" && courseLevel.toString() != "Select Your Course Level") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("Register", response)
                    if (response == "success") {
                        Toast.makeText(
                            requireContext(),
                            "Success",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (response == "failure") {
                        Toast.makeText(
                            requireContext(),
                            "Error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        requireContext(),
                        error.toString().trim(),
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["name"] = name!!
                    data["email"] = email!!
                    data["password"] = password!!
                    data["courseLevel"] = courseLevel.toString()!!
                    return data
                }
            }

            val requestQueue = Volley.newRequestQueue(requireContext())
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(requireActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    fun login(view: View?) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}