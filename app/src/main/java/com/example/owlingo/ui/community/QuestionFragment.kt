package com.example.owlingo.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.community.Question
import com.example.owlingo.databinding.FragmentQuestionBinding
import com.google.android.material.appbar.MaterialToolbar

class QuestionFragment : Fragment(), ClickListener {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var navController: NavController
    private lateinit var clickListener: ClickListener

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

        clickListener = this
        val application = requireNotNull(this.activity).application
        val viewModelFactory = QuestionFactory(
            QuestionFragmentArgs.fromBundle(requireArguments()).questionId, application
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(QuestionViewModel::class.java)
        binding.questionViewModel = viewModel

        val adapter = CommentAdapter(clickListener)
        binding.commentList.adapter = adapter
        viewModel.commentList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.addComment.setOnClickListener {
            val action = QuestionFragmentDirections.actionNavigationCreateComment()
            action.questionId = QuestionFragmentArgs.fromBundle(requireArguments()).questionId
            NavHostFragment.findNavController(this).navigate(action)
        }

        val topAppBar: MaterialToolbar = binding.topAppBar
        navController = NavHostFragment.findNavController(this)
        (activity as AppCompatActivity).setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            val action =
                QuestionFragmentDirections.actionNavigationCommunity()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.refresh(QuestionFragmentArgs.fromBundle(requireArguments()).questionId)

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onClick(any: Any, action: String?) {
        if(action=="edit") {
            val comment = any as Comment
            val action = QuestionFragmentDirections.actionNavigationEditComment()
            action.commentId = comment.commentId
            action.questionId = comment.questionId
            NavHostFragment.findNavController(this).navigate(action)
        }

        if(action=="del") {
            val commentId = any as Int
            showConfirmationDialog(commentId)
        }
    }

    private fun showConfirmationDialog(commentId: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.confirmation_dialog, null)

         val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
         val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)
         titleTextView.text = "Sure to Delete the Comment?"
         messageTextView.text = "Once delete cannot Undo the action"

        builder.setView(dialogView)
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteComment(commentId)
            viewModel.refresh(QuestionFragmentArgs.fromBundle(requireArguments()).questionId)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

}