package com.example.owlingo.ui.community

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
import com.example.owlingo.databinding.FragmentCreateQuestionBinding
import com.google.android.material.appbar.MaterialToolbar

class CreateQuestionFragment : Fragment(){

    private lateinit var viewModel: CreateQuestionViewModel
    private lateinit var viewModelFactory: CreateQuestionFactory
    private lateinit var navController: NavController
    private lateinit var courseListAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateQuestionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_question,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        viewModelFactory = CreateQuestionFactory(
            CreateQuestionFragmentArgs.fromBundle(requireArguments()).userId, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreateQuestionViewModel::class.java)
        binding.createQuestionViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.btnSave.setOnClickListener {
            val questionTitle = binding.questionTV.text.toString()
            val questionText = binding.questionExplainTV.text.toString()
            viewModel.updateQuestionDetail(questionTitle, questionText)
            val action = CreateQuestionFragmentDirections.actionNavigationCommunity()
            NavHostFragment.findNavController(this).navigate(action)
        }

        courseListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        courseListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.courseSelect.adapter = courseListAdapter
        binding.courseSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = parent?.getItemAtPosition(position).toString()
                viewModel._courseName.value = selectedValue
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