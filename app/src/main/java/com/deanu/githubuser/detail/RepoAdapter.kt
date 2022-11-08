package com.deanu.githubuser.detail

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deanu.githubuser.common.domain.model.UserRepo
import com.deanu.githubuser.databinding.RepositoryItemBinding
import java.text.SimpleDateFormat
import java.util.*

class RepoAdapter : ListAdapter<UserRepo, RepoAdapter.ViewHolder>(DIFF_CALLBACK) {
  class ViewHolder(private val binding: RepositoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userRepo: UserRepo) {
      binding.tvTitle.text = userRepo.repoName
      if (userRepo.repoDescription.isNotEmpty()) {
        binding.tvDescription.text = userRepo.repoDescription
      } else {
        binding.tvDescription.visibility = View.GONE
      }
      binding.tvUpdateTime.text = parseRelativeTime(userRepo)
      binding.tvStargazerCount.text = userRepo.stargazersCount.toString()
    }

    private fun parseRelativeTime(userRepo: UserRepo): CharSequence {
      val currentLocale = ConfigurationCompat.getLocales(binding.root.resources.configuration)[0]
      val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", currentLocale)
      val date: Date? = inputFormat.parse(userRepo.updatedAt)

      var timeInLong: Long = Calendar.getInstance().timeInMillis
      date?.time?.let { time ->
        timeInLong = time
      }

      return DateUtils.getRelativeTimeSpanString(
        timeInLong,
        Calendar.getInstance().timeInMillis,
        DateUtils.MINUTE_IN_MILLIS
      )
    }

    companion object {
      fun inflateFrom(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RepositoryItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.inflateFrom(parent)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val userRepo = getItem(position)
    holder.bind(userRepo)
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserRepo>() {
      override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
        return oldItem == newItem
      }
    }
  }
}