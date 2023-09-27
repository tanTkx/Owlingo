package com.example.owlingo.ui.community

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.owlingo.databinding.FragmentCreateQuestionBinding
import com.example.owlingo.databinding.FragmentEditCommentBinding
import com.google.android.material.appbar.MaterialToolbar

class EditCommentFragment : Fragment() {

    private lateinit var viewModel: EditCommentViewModel
    private lateinit var viewModelFactory: EditCommentFactory
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEditCommentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_comment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        viewModelFactory = EditCommentFactory(
            EditCommentFragmentArgs.fromBundle(requireArguments()).commentId, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditCommentViewModel::class.java)
        binding.editCommentViewModel = viewModel

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
            val action = EditCommentFragmentDirections.actionNavigationViewQuestion()
            action.questionId = EditCommentFragmentArgs.fromBundle(requireArguments()).questionId
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