package com.example.owlingo.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.Question
import com.example.owlingo.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment(), ClickListener {

    private lateinit var binding: FragmentCommunityBinding
    private lateinit var viewModel: CommunityViewModel
    private lateinit var clickListener: ClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_community,
            container,
            false
        )
        clickListener = this
        val application = requireNotNull(this.activity).application
        val viewModelFactory = CommunityFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CommunityViewModel::class.java)
        binding.communityViewModel = viewModel

        val adapter = CommunityAdapter(clickListener)
        binding.questionList.adapter = adapter
        viewModel.questionList.observe(viewLifecycleOwner, Observer {
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
        viewModel.refreshQuestionList(1)

        binding.delBtn.setOnClickListener{
            val action = CommunityFragmentDirections.actionNavigationCreateQuestion()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onClick(any: Any) {
        val question = any as Question
        val action = CommunityFragmentDirections.actionNavigationViewQuestion()
        action.questionId = question.questionId
        NavHostFragment.findNavController(this).navigate(action)
    }

}
