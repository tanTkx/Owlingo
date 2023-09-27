package com.example.owlingo.ui.community

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
import com.example.owlingo.databinding.AnswerCardBinding

class CommentAdapter(
    private val clickListener: ClickListener
) : ListAdapter<Comment, CommentAdapter.ViewHolder>(CommentDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    class ViewHolder private constructor(
        private val binding: AnswerCardBinding,
        private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Comment) {
            binding.comment = item
            binding.executePendingBindings()
            binding.editBtn.setOnClickListener{
                clickListener.onClick(item, "edit")
            }
            binding.delBtn.setOnClickListener{
                clickListener.onClick(item.commentId, "del")
            }
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: ClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    AnswerCardBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, clickListener)
            }
        }
    }
}

class CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.equals(newItem)
    }
}
