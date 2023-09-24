package com.example.owlingo.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.databinding.FragmentQuestionBinding
import com.google.android.material.appbar.MaterialToolbar

class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_question,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val viewModelFactory = QuestionFactory(
            QuestionFragmentArgs.fromBundle(requireArguments()).questionId, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(QuestionViewModel::class.java)
        binding.questionViewModel = viewModel


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