package com.example.owlingo.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.owlingo.R
import com.example.owlingo.ui.UserInformation
import org.json.JSONException
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AccountFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var etName: TextView
    private lateinit var etEmail: TextView
    private val URL: String = "http://10.0.2.2/Owlingo/getUser.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.account_fragment, container, false)
        etName = view.findViewById(R.id.textView)
        etEmail = view.findViewById(R.id.textView3)

        val button = view.findViewById<TextView>(R.id.textView5)

        button.setOnClickListener {
            // Navigate to the LoginFragment using the action defined in the navigation graph
            findNavController().navigate(R.id.action_accountFragment_to_profileFragment)
        }

        val delete_btn = view.findViewById<TextView>(R.id.textView4)

        delete_btn.setOnClickListener {
            val userID = UserInformation.userID

            if (userID != null) {
                userID.value?.let { it1 -> deleteUserData(it1) }
            };

        }

        val logout_btn = view.findViewById<Button>(R.id.register_btn)

        logout_btn.setOnClickListener {
            UserInformation.setUserID("-1")

            Toast.makeText(
                requireActivity(),
                "Logout Successfully !!",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_accountFragment_to_loginFragment)

        }

        val backButton = view.findViewById<ImageButton>(R.id.back_btn)

        // Set an OnClickListener to handle button click
        backButton.setOnClickListener {
            // Navigate to another fragment (you'll need to replace YourNextFragment with the actual fragment)
            findNavController().navigate(R.id.action_accountFragment_to_userAllCourseFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch user data when the view is created
        val userID = UserInformation.userID

        if (userID != null) {
            userID.value?.let { fetchUserData(it) }
        }
    }

    private fun fetchUserData(userID: String) {
        val getUserURL = "http://10.0.2.2/Owlingo/getUser.php?userID=$userID"

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, getUserURL,
            Response.Listener { response ->
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

    private fun deleteUserData(userID: String) {
        val getUserURL = "http://10.0.2.2/Owlingo/deleteUser.php?userID=$userID"

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, getUserURL,
            Response.Listener { response ->
                Log.d("FetchData", response)

                // Check if the response is "success"
                if (response.trim() == "Success") {
                    // Navigate to the desired destination when the response is successful
                    findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
                }
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
                etName.text = name.trim()
            }

            if (jsonResponse.has("email")) {
                val email = jsonResponse.getString("email")
                etEmail.text = email.trim()
            }

            // ... handle other user data

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
