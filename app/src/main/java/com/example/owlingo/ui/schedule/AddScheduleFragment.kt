package com.example.owlingo.ui.schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    private lateinit var courseListAdapter: ArrayAdapter<String>

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
            val selectedCourse = binding.spinnerCourse.selectedItem.toString()
            val selectedDay = binding.spinnerDay.selectedItem.toString()
            val selectedStartTime = binding.spinnerStartTime.selectedItem.toString()
            val selectedEndTime = binding.spinnerEndTime.selectedItem.toString()
            viewModel.updateScheduleDetail(selectedCourse, selectedDay,selectedStartTime,selectedEndTime)
            val action = AddScheduleFragmentDirections.actionNavigationAddScheduleToNavigationManageSchedule()
            NavHostFragment.findNavController(this).navigate(action)
        }

        val scheduleDayArrayAdapter = ArrayAdapter.createFromResource(
            application,
            R.array.scheduleDay,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerDay.adapter=scheduleDayArrayAdapter
        binding.spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = parent?.getItemAtPosition(position).toString()
                viewModel._selectedDay.value = selectedValue
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Selected", "Nothing Selected")
            }
        }


        val scheduleStartTimeArrayAdapter = ArrayAdapter.createFromResource(
            application,
            R.array.scheduleStartTime,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerStartTime.adapter=scheduleStartTimeArrayAdapter
        binding.spinnerStartTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = parent?.getItemAtPosition(position).toString()
                viewModel._selectedStartTime.value = selectedValue
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Selected", "Nothing Selected")
            }
        }


        val scheduleEndTimeArrayAdapter = ArrayAdapter.createFromResource(
            application,
            R.array.scheduleEndTime,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerEndTime.adapter=scheduleEndTimeArrayAdapter
        binding.spinnerEndTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = parent?.getItemAtPosition(position).toString()
                viewModel._selectedEndTime.value = selectedValue
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Selected", "Nothing Selected")
            }
        }


        courseListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        courseListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCourse.adapter = courseListAdapter
        binding.spinnerCourse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = parent?.getItemAtPosition(position).toString()
                viewModel._selectedCourse.value = selectedValue
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Selected", "Nothing Selected")
            }
        }

        viewModel.courseList.observe(viewLifecycleOwner) { courseList ->
            courseListAdapter.clear()
            courseListAdapter.addAll(courseList)
            courseListAdapter.notifyDataSetChanged()
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