package com.example.owlingo.ui.community

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.owlingo.R
import com.example.owlingo.database.community.QuestionDatabase
import com.example.owlingo.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCommunityBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_community, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = QuestionDatabase.getInstance(application).questionDatabaseDao
        val viewModelFactory = CommunityFactory(dataSource, application)

        val communityViewModel =
            ViewModelProvider(this, viewModelFactory)[CommunityViewModel::class.java]

        binding.communityViewModel = communityViewModel

        val adapter = CommunityAdapter()
        binding.questionList.adapter = adapter

        communityViewModel.questions.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this


        return binding.root
    }
}