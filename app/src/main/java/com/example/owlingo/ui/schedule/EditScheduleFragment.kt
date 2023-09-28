package com.example.owlingo.ui.schedule

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.databinding.EditScheduleBinding
import com.google.android.material.appbar.MaterialToolbar




class EditScheduleFragment : Fragment() {

    private lateinit var viewModel: EditScheduleViewModel
    private lateinit var viewModelFactory: EditScheduleFactory
    private lateinit var navController: NavController
    private lateinit var courseListAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: EditScheduleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_schedule,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        viewModelFactory = EditScheduleFactory(
            EditScheduleFragmentArgs.fromBundle(requireArguments()).scheduleID, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditScheduleViewModel::class.java)
        binding.editScheduleViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        val scheduleDayArrayAdapter = ArrayAdapter.createFromResource(
            application,
            R.array.scheduleDay,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerDay.adapter = scheduleDayArrayAdapter
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            for (i in 0 until scheduleDayArrayAdapter.count) {
                if ( viewModel.selectedDay.value.toString()
                        .equals(scheduleDayArrayAdapter.getItem(i).toString())
                ) {
                    binding.spinnerDay.setSelection(i)
                    break
                }
            }
        }
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
        binding.spinnerStartTime.adapter = scheduleStartTimeArrayAdapter
        viewModel.selectedStartTime.observe(viewLifecycleOwner) {
            for (i in 0 until scheduleStartTimeArrayAdapter.count) {
                if ( viewModel.selectedStartTime.value.toString()
                        .equals(scheduleStartTimeArrayAdapter.getItem(i).toString())
                ) {
                    binding.spinnerStartTime.setSelection(i)
                    break
                }
            }
        }
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

        binding.spinnerEndTime.adapter = scheduleEndTimeArrayAdapter
        viewModel.selectedEndTime.observe(viewLifecycleOwner) {
            for (i in 0 until scheduleEndTimeArrayAdapter.count) {
                if ( viewModel.selectedEndTime.value.toString()
                        .equals(scheduleEndTimeArrayAdapter.getItem(i).toString())
                ) {
                    binding.spinnerEndTime.setSelection(i)
                    break
                }
            }
        }
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
        viewModel.courseList.observe(viewLifecycleOwner) { courseList ->
            courseListAdapter.clear()
            courseListAdapter.addAll(courseList)
            courseListAdapter.notifyDataSetChanged()
            for (i in 0 until courseListAdapter.count) {
                if ( viewModel.selectedCourse.value.toString()
                        .equals(courseListAdapter.getItem(i).toString())
                ) {
                    binding.spinnerCourse.setSelection(i)
                    break
                }
            }
        }
        binding.spinnerCourse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = parent?.getItemAtPosition(position).toString()
                viewModel._selectedCourse.value = selectedValue
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Selected", "Nothing Selected")
            }
        }


        binding.btnUpdate.setOnClickListener {
            val selectedCourse = binding.spinnerCourse.selectedItem.toString()
            val selectedDay = binding.spinnerDay.selectedItem.toString()
            val selectedStartTime = binding.spinnerStartTime.selectedItem.toString()
            val selectedEndTime = binding.spinnerEndTime.selectedItem.toString()
            viewModel.updateScheduleDetail(selectedCourse, selectedDay,selectedStartTime,selectedEndTime)
            val action = EditScheduleFragmentDirections.actionNavigationEditScheduleToNavigationManageSchedule()
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