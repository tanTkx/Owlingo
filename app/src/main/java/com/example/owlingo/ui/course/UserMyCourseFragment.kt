package com.example.owlingo.ui.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.owlingo.R
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.course.Course
import com.example.owlingo.databinding.FragmentAdminCourseDetailBinding
import com.example.owlingo.databinding.FragmentMyCourseBinding
import com.example.owlingo.databinding.FragmentUserCourseDetailBinding


class UserMYCourseFragment : Fragment(), ClickListener {

    private lateinit var binding: FragmentMyCourseBinding
    private lateinit var viewModel: UserMyCourseViewModel
    private lateinit var clickListener: ClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_course,
            container,
            false
        )

        clickListener = this
        val application = requireNotNull(this.activity).application
        val viewModelFactory = UserMyCourseFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(UserMyCourseViewModel::class.java)
        binding.userMyCourseViewModel = viewModel

        val adapter = UserMyCourseAdapter(clickListener)
        binding.courseList.adapter = adapter
        viewModel.courseList.observe(viewLifecycleOwner, Observer {
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
        viewModel.refreshCourseList()
        binding.lifecycleOwner = this
        return binding.root
    }
    //
    override fun onClick(any: Any, action: String?) {
        val course = any as Course
//        if( action=="edit"){
//            val action = UserAllCourseFragmentDirections.actionNavigationAdminViewAllCourseToNavigationAdminEditCourse()
//            action.courseId = course.course_id
//            NavHostFragment.findNavController(this).navigate(action)
//        }
    }

}