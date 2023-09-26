package com.example.owlingo.ui.community

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.databinding.FragmentCreateCommentBinding
import com.google.android.material.appbar.MaterialToolbar

class CreateCommentFragment : Fragment() {

    private lateinit var viewModel: CreateCommentViewModel
    private lateinit var viewModelFactory: CreateCommentFactory
    private lateinit var navController: NavController

    @SuppressLint("ServiceCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateCommentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_comment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        viewModelFactory = CreateCommentFactory(
            CreateCommentFragmentArgs.fromBundle(requireArguments()).userId,
            CreateCommentFragmentArgs.fromBundle(requireArguments()).questionId,
        application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreateCommentViewModel::class.java)
        binding.createCommentViewModel = viewModel

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.btnSave.setOnClickListener {
            val commentTitle = binding.answerTV.text.toString()
            val commentText = binding.commentExplainTV.text.toString()
            viewModel.updateCommentDetail(commentTitle, commentText)
            val action = CreateCommentFragmentDirections.actionNavigationViewQuestion()
            action.questionId = CreateCommentFragmentArgs.fromBundle(requireArguments()).questionId
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