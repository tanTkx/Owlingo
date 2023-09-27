package com.example.owlingo.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.R
import com.example.owlingo.ui.UserInformation
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etCourseLevel: Spinner
    private lateinit var btnUpdate: Button

    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var courseLevel: String? = ""

    private val URL: String = "http://10.0.2.2/Owlingo_php/update.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.profile_fragment, container, false)

        // Assume you have a user ID, replace '123' with the actual user ID
        val userID = UserInformation.userID
        etEmail = rootView.findViewById(R.id.email_edit)
        etName = rootView.findViewById(R.id.name_edit)
        etPassword = rootView.findViewById(R.id.password_edit)
        etCourseLevel = rootView.findViewById(R.id.spinner)

        // Fetch user data based on the userID
        if (userID != null) {
            fetchUserData(userID)
        }

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

        btnUpdate = rootView.findViewById(R.id.register_btn)

        btnUpdate.setOnClickListener {
            updateUserData()
        }

        val backButton = rootView.findViewById<ImageButton>(R.id.back_btn)

        // Set an OnClickListener to handle button click
        backButton.setOnClickListener {
            // Navigate to another fragment (you'll need to replace YourNextFragment with the actual fragment)
            findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch user data when the view is created
        val userID = UserInformation.userID

        if (userID != null) {
            fetchUserData(userID)
        }
    }

    private fun fetchUserData(userID: String) {
        val getUserURL = "http://10.0.2.2/Owlingo_php/getUser.php?userID=$userID"

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, getUserURL,
            Response.Listener { response ->
                Log.d("FetchData", response)
                handleUserResponse(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    requireActivity(),
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            override fun getParams(): Map<String, String>? {
                return null
            }
        }

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }

    private fun handleUserResponse(response: String) {
        try {
            val jsonResponse = JSONObject(response)

            if (jsonResponse.has("name")) {
                val name = jsonResponse.getString("name")
                etName.setText(name.trim())
            }

            if (jsonResponse.has("email")) {
                val email = jsonResponse.getString("email")
                etEmail.setText(email.trim())
            }

            if (jsonResponse.has("password")) {
                val password = jsonResponse.getString("password")
                etPassword.setText(password.trim())
            }

            if (jsonResponse.has("courseLevel")) {
                val courseLevel = jsonResponse.getString("courseLevel")

                // Find the index of the course level in the array
                val courseLevelArray = resources.getStringArray(R.array.CoursesLevel)
                val index = courseLevelArray.indexOf(courseLevel)

                // Set the spinner selection
                etCourseLevel.setSelection(index)
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

    private fun updateUserData() {
        name = etName.text.toString().trim()
        email = etEmail.text.toString().trim()
        password = etPassword.text.toString().trim()
        courseLevel = etCourseLevel.selectedItem.toString().trim()
        val userID = UserInformation.userID
        Log.d("Test", "1")
        if (name != "" && email != "" && password != "" && courseLevel.toString() != "" && userID != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("Update", response)
                    if (response == "success") {
                        Toast.makeText(
                            requireContext(),
                            "Update Successfully !!",
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
                    } else if (response == "failure") {
                        Toast.makeText(
                            requireContext(),
                            "Update Fail !!",
                            Toast.LENGTH_SHORT
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
                    data["courseLevel"] = courseLevel!!
                    data["userID"] = userID!!
                    return data
                }
            }

            val requestQueue = Volley.newRequestQueue(requireContext())
            requestQueue.add(stringRequest)
        }
    }
}
