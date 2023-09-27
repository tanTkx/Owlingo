package com.example.owlingo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StartFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the BottomNavigationView from the MainActivity
        val bottomNavigationView = activity?.findViewById<View>(R.id.nav_view)

        // Hide the BottomNavigationView
        bottomNavigationView?.visibility = View.GONE

        val button = view.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            // Navigate to the LoginFragment using the action defined in the navigation graph
            findNavController().navigate(R.id.action_StartFragment_to_loginFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_fragment, container, false)
    }
}
