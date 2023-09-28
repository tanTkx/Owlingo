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
import com.example.owlingo.databinding.FragmentEditCourseBinding
import com.example.owlingo.ui.community.CreateQuestionFactory
import com.example.owlingo.ui.community.CreateQuestionFragmentArgs
import com.example.owlingo.ui.community.CreateQuestionFragmentDirections
import com.example.owlingo.ui.community.CreateQuestionViewModel
import com.example.owlingo.ui.community.EditCommentFragmentArgs
import com.example.owlingo.ui.community.EditCommentFragmentDirections
import com.google.android.material.appbar.MaterialToolbar

class AdminEditCourseFragment : Fragment(){

    private lateinit var viewModel: AdminEditCourseViewModel
    private lateinit var viewModelFactory: AdminEditCourseFactory
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEditCourseBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_course,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        viewModelFactory = AdminEditCourseFactory(
            AdminEditCourseFragmentArgs.fromBundle(requireArguments()).courseId,
            application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AdminEditCourseViewModel::class.java)
        binding.adminEditCourseViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.btnSave.setOnClickListener {
            val courseName = binding.etCn.text.toString()
            val courseLecture = binding.etLn.text.toString()
            val courseDetail = binding.etDes.text.toString()
            val courseFee = binding.etPrice.text.toString().toInt()

            viewModel.editCourse(courseName, courseDetail, courseLecture, courseFee)

            val action = AdminEditCourseFragmentDirections.actionNavigationAdminEditCourseToNavigationAdminViewAllCourse()
            NavHostFragment.findNavController(this).navigate(action)
        }

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