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


class AdminAllCourseFragment : Fragment(), ClickListener {

    private lateinit var binding: FragmentAdminCourseDetailBinding
    private lateinit var viewModel: AdminAllCourseViewModel
    private lateinit var clickListener: ClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_admin_course_detail,
            container,
            false
        )

        clickListener = this
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AdminAllCourseFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AdminAllCourseViewModel::class.java)
        binding.adminAllCourseViewModel = viewModel

        val adapter = AdminAllCourseAdapter(clickListener)
        binding.courseList.adapter = adapter
        viewModel.courseList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
//
//        viewModel.getToastMessage().observe(viewLifecycleOwner) { message ->
//            if (message != null) {
//                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                viewModel.toastShown()
//            }
//        }
//        viewModel.refreshQuestionList(1)
//
//        binding.createBtn.setOnClickListener{
//            val action = CommunityFragmentDirections.actionNavigationCreateQuestion()
//            action.userId = 1
//            NavHostFragment.findNavController(this).navigate(action)
//        }
//
        binding.lifecycleOwner = this
        return binding.root
    }
//
    override fun onClick(any: Any, action: String?) {
        val question = any as Course
//        val action = .actionNavigationViewQuestion()
//        action.questionId = question.questionId
//        NavHostFragment.findNavController(this).navigate(action)
    }

}
