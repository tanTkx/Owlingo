package com.example.owlingo.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.schedule.Schedule
import com.example.owlingo.databinding.FragmentCommunityBinding
import com.example.owlingo.databinding.ManageScheduleBinding
import com.example.owlingo.ui.community.CommunityViewModel
import com.example.owlingo.ui.community.QuestionFragmentArgs
import com.example.owlingo.ui.schedule.ScheduleAdapter
import com.google.android.material.appbar.MaterialToolbar


class ScheduleFragment  : Fragment(), ClickListener {

    private lateinit var binding: ManageScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var clickListener: ClickListener
    private lateinit var navController: NavController

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

        viewModel.refresh()

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

        val topAppBar: MaterialToolbar = binding.topAppBar
        navController = NavHostFragment.findNavController(this)
        (activity as AppCompatActivity).setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onClick(any: Any, action: String?) {
        if(action == "edit"){
            val schedule = any as Schedule
            val action = ScheduleFragmentDirections.actionNavigationManageScheduleToNavigationEditSchedule()
            action.scheduleID = schedule.scheduleID.toInt()
            NavHostFragment.findNavController(this).navigate(action)
        }
       if(action=="del"){
           val schedule = any as Schedule
           showConfirmationDialog(schedule.scheduleID.toInt() )
           viewModel.refresh()
       }
    }

    private fun showConfirmationDialog(scheduleID: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.confirmation_dialog, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)
        titleTextView.text = "Sure to Delete the Schedule?"
        messageTextView.text = "Once delete cannot Undo the action"

        builder.setView(dialogView)
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteSchedule(scheduleID)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

}