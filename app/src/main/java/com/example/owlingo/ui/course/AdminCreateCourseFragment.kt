package com.example.owlingo.ui.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.database.course.Course
import com.example.owlingo.databinding.FragmentCreateCourseBinding
import com.example.owlingo.databinding.FragmentCreateQuestionBinding
import com.example.owlingo.ui.community.CreateQuestionFactory
import com.example.owlingo.ui.community.CreateQuestionFragmentArgs
import com.example.owlingo.ui.community.CreateQuestionFragmentDirections
import com.example.owlingo.ui.community.CreateQuestionViewModel
import com.google.android.material.appbar.MaterialToolbar

class AdminCreateCourseFragment : Fragment(){

    private lateinit var viewModel: AdminCreateCourseViewModel
    private lateinit var viewModelFactory: AdminCreateCourseFactory
    private lateinit var navController: NavController
    private lateinit var courseListAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateCourseBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_course,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        viewModelFactory = AdminCreateCourseFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AdminCreateCourseViewModel::class.java)
        binding.adminCreateCourseViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.btnCreate.setOnClickListener {
            val courseName = binding.etCn.text.toString()
            val courseLecture = binding.etLn.text.toString()
            val courseDetail = binding.etDes.text.toString()
            val courseFee = binding.etPrice.text.toString().toInt()

            viewModel.createCourse(courseName, courseLecture, courseDetail, courseFee)
//
//            val action = CreateQuestionFragmentDirections.actionNavigationCommunity()
//            NavHostFragment.findNavController(this).navigate(action)
        }

        courseListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        courseListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val topAppBar: MaterialToolbar = binding.topAppBar
        navController = NavHostFragment.findNavController(this)
        (activity as AppCompatActivity).setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        binding.lifecycleOwner = this
        return binding.root
    }

}