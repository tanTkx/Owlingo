package com.example.owlingo.ui.schedule

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
import com.example.owlingo.database.schedule.Schedule
import com.example.owlingo.databinding.FragmentCommunityBinding
import com.example.owlingo.databinding.ManageScheduleBinding
import com.example.owlingo.ui.community.CommunityViewModel
import com.example.owlingo.ui.schedule.ScheduleAdapter


class ScheduleFragment  : Fragment(), ClickListener {

    private lateinit var binding: ManageScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var clickListener: ClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.manage_schedule,
            container,
            false
        )
        clickListener = this
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ScheduleFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)
        binding.scheduleViewModel = viewModel

        val adapter = ScheduleAdapter(clickListener)

        binding.scheduleList.adapter = adapter
        viewModel.scheduleList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.addScheduleButton.setOnClickListener{
            val action = ScheduleFragmentDirections.actionNavigationManageScheduleToNavigationAddSchedule()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.toastShown()
            }
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onClick(any: Any) {
        val question = any as Schedule
//        val action = ScheduleFragmentDirections.actionNavigationViewQuestion()
//        action.scheduleID = question.scheduleID
//        NavHostFragment.findNavController(this).navigate(action)
    }

}