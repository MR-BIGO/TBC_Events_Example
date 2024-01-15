package com.example.tbc_events.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbc_events.R
import com.example.tbc_events.databinding.UsersListItemBinding
import com.example.tbc_events.presentation.model.UserItem

class UsersRecyclerAdapter :
    ListAdapter<UserItem, UsersRecyclerAdapter.UsersRecyclerViewHolder>(DiffCallback()) {

    var itemOnClick: ((Int) -> Unit)? = null
    var itemOnLongClick: ((Int) -> Unit)? = null

    inner class UsersRecyclerViewHolder(private val binding: UsersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val user: UserItem = currentList[adapterPosition]
            with(binding) {
                tvEmail.text = user.email
                tvFirstName.text = user.firstName
                tvLastName.text = user.lastName
                Glide.with(itemView.context).load(user.img).into(ivUserProfile)
                if (user.isSelected) {
                    root.setBackgroundResource(R.drawable.list_item_shape_selected)
                } else {
                    root.setBackgroundResource(R.drawable.list_item_shape)
                }
            }
        }

        fun listener() {
            binding.root.setOnClickListener {
                itemOnClick!!(currentList[adapterPosition].id)
            }
            binding.root.setOnLongClickListener {
                itemOnLongClick!!.invoke(currentList[adapterPosition].id)
//                currentList[adapterPosition].isSelected = !currentList[adapterPosition].isSelected
                notifyItemChanged(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRecyclerViewHolder {
        return UsersRecyclerViewHolder(
            UsersListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersRecyclerViewHolder, position: Int) {
        holder.bind()
        holder.listener()
    }

    fun setData(users: List<UserItem>) {
        submitList(users)
    }

//    fun getSelectedItems(): List<Int> {
//        val selectedItems: MutableList<Int> = mutableListOf()
//        for (i in currentList) {
//            if (i.isSelected) {
//                selectedItems.add(i.id)
//            }
//        }
//        return selectedItems
//    }

    private class DiffCallback : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
            return oldItem == newItem
        }
    }
}