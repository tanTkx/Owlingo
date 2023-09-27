package com.example.owlingo.ui.schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.databinding.AddScheduleBinding
import com.example.owlingo.databinding.FragmentCreateCommentBinding
import com.example.owlingo.ui.community.CreateCommentFactory
import com.example.owlingo.ui.community.CreateCommentFragmentArgs
import com.example.owlingo.ui.community.CreateCommentFragmentDirections
import com.example.owlingo.ui.community.CreateCommentViewModel
import com.google.android.material.appbar.MaterialToolbar

class AddScheduleFragment : Fragment() {

    private lateinit var viewModel: AddScheduleViewModel
    private lateinit var viewModelFactory: AddScheduleFactory
    private lateinit var navController: NavController

    @SuppressLint("ServiceCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: AddScheduleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_schedule,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        viewModelFactory = AddScheduleFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddScheduleViewModel::class.java)

        binding.addScheduleViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.btnCreate.setOnClickListener {
            val selectedCourse = ""
            val selectedDay = ""
            val selectedStartTime =""
            val selectedEndTime = ""
            viewModel.updateScheduleDetail(selectedCourse, selectedDay,selectedStartTime,selectedEndTime)
            val action = AddScheduleFragmentDirections.actionNavigationAddScheduleToNavigationManageSchedule()
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