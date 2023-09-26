package com.example.owlingo.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.Question
import com.example.owlingo.databinding.CommunityCardBinding

class CommunityAdapter(
    private val clickListener: ClickListener
) : ListAdapter<Question, CommunityAdapter.ViewHolder>(QuestionDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    class ViewHolder private constructor(
        private val binding: CommunityCardBinding,
        private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Question) {
            binding.question = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: ClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    CommunityCardBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, clickListener)
            }
        }
    }
}

class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.questionId == newItem.questionId
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.equals(newItem)
    }
}