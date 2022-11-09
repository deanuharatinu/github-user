package com.deanu.githubuser.home

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deanu.githubuser.R
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.databinding.UserItemBinding

class UserAdapter constructor(
  private val clickListener: (username: String) -> Unit
) : ListAdapter<User, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

  class ViewHolder(
    private val binding: UserItemBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
      user: User,
      clickListener: (username: String) -> Unit
    ) {
      binding.tvUsername.text = user.username
      Glide.with(binding.root)
        .load(user.photoUrl)
        .circleCrop()
        .placeholder(ColorDrawable(getColor(binding.root.context, R.color.neutral_50)))
        .into(binding.ivAvatar)

      binding.userItem.setOnClickListener {
        clickListener(user.username)
      }
    }

    companion object {
      fun inflateFrom(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.inflateFrom(parent)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val user = getItem(position)
    user?.let {
      holder.bind(it, clickListener)
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
      override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
      }
    }
  }
}