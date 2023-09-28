package com.example.owlingo.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.owlingo.component.ClickListener
import com.example.owlingo.database.community.CommentWithUser
import com.example.owlingo.databinding.AnswerCardBinding

class CommentWithUserAdapter(
    private val clickListener: ClickListener
) : ListAdapter<CommentWithUser, CommentWithUserAdapter.ViewHolder>(CommentWithUserDiffCallback()) {

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

        fun bind(item: CommentWithUser) {
            binding.commentWithUser = item
            binding.executePendingBindings()
            binding.editBtn.setOnClickListener{
                clickListener.onClick(item, "edit")
            }
            binding.delBtn.setOnClickListener{
                clickListener.onClick(item, "del")
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

class CommentWithUserDiffCallback : DiffUtil.ItemCallback<CommentWithUser>() {
    override fun areItemsTheSame(oldItem: CommentWithUser, newItem: CommentWithUser): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: CommentWithUser, newItem: CommentWithUser): Boolean {
        return oldItem.equals(newItem)
    }
}
