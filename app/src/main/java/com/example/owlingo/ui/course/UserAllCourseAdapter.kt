package com.example.owlingo.ui.course

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.owlingo.R
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.Comment
import com.example.owlingo.database.course.Course
import com.example.owlingo.databinding.AllCourseCardBinding
import com.example.owlingo.databinding.AnswerCardBinding
import com.example.owlingo.databinding.UserAllCourseCardBinding

class UserAllCourseAdapter(
    private val clickListener: ClickListener
) : ListAdapter<Course, UserAllCourseAdapter.ViewHolder>(UserCourseDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    class ViewHolder private constructor(
        private val binding: UserAllCourseCardBinding,
        private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Course) {
            binding.course = item
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                clickListener.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: ClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    UserAllCourseCardBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, clickListener)
            }
        }
    }
}

class UserCourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.course_id == newItem.course_id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.equals(newItem)
    }
}
