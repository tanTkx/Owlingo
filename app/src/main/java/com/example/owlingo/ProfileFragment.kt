package com.example.owlingo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etCourseLevel: Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.profile_fragment, container, false)

        // Assume you have a user ID, replace '123' with the actual user ID
        val userID = "1"
        etEmail = rootView.findViewById(R.id.email_edit)
        etName = rootView.findViewById(R.id.name_edit)
        etPassword = rootView.findViewById(R.id.password_edit)
        etCourseLevel = rootView.findViewById(R.id.spinner)

        // Fetch user data based on the userID
        fetchUserData(userID)

        // Find the Spinner within the fragment's layout
        val spinner: Spinner = rootView.findViewById(R.id.spinner)

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

        return rootView
    }

    private fun fetchUserData(userID: String) {
        val getUserURL = "http://10.0.2.2/Owlingo_php/getUser.php?userID=$userID"

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, getUserURL,
            Response.Listener { response ->
                Log.d("FetchData", response)

                // Parse the JSON response
                try {
                    val jsonObject = JSONObject(response)

                    if (jsonObject.has("name")) {
                        val name = jsonObject.getString("name")
                        // Handle the name
                        etName.setText(name.trim())
                    }

                    if (jsonObject.has("email")) {
                        val email = jsonObject.getString("email")
                        // Handle the email
                        etEmail.setText(email.trim())
                    }

                    if (jsonObject.has("courseLevel")) {
                        val courseLevel = jsonObject.getString("courseLevel")
                        val adapter = etCourseLevel.adapter as ArrayAdapter<String>
                        val position = adapter.getPosition(courseLevel)
                        etCourseLevel.setSelection(position)
                    }


                    if (jsonObject.has("password")) {
                        val password = jsonObject.getString("password")
                        // Handle the email
                        etPassword.setText(password.trim())
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }) {
            // No request params needed for a GET request
            override fun getParams(): Map<String, String>? {
                return null
            }
        }

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }
}
