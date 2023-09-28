package com.example.owlingo.ui.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.course.Course
import com.example.owlingo.databinding.FragmentAdminCourseDetailBinding
import com.example.owlingo.databinding.FragmentCourseDetailBinding
import com.example.owlingo.databinding.FragmentUserCourseDetailBinding
import com.example.owlingo.ui.community.QuestionFragmentArgs
import com.example.owlingo.ui.community.QuestionFragmentDirections
import com.google.android.material.appbar.MaterialToolbar


class UserDetailCourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseDetailBinding
    private lateinit var viewModel: UserDetailCourseViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_course_detail,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = UserDetailCourseFactory( UserDetailCourseFragmentArgs.fromBundle(requireArguments()).courseId, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(UserDetailCourseViewModel::class.java)
        binding.userDetailCourseViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        viewModel.isVisible.observe(viewLifecycleOwner) {
            binding.btnUnlock.isVisible = (viewModel.isVisible.value == "visible")
        }

        binding.btnUnlock.setOnClickListener{
            viewModel.unlockCourse()
            val action = UserDetailCourseFragmentDirections.actionUserDetailCourseFragmentToUserAllCourseFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        val topAppBar: MaterialToolbar = binding.topAppBar
        navController = NavHostFragment.findNavController(this)
        (activity as AppCompatActivity).setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
