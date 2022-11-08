package com.deanu.githubuser.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deanu.githubuser.R
import com.deanu.githubuser.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
  private var _binding: FragmentDetailBinding? = null
  private val binding get() = _binding!!
  private val viewModel: DetailViewModel by viewModels()
  private val args: DetailFragmentArgs by navArgs()
  private lateinit var adapter: RepoAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDetailBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initHeader()
    initRecyclerView()
    initObserver()
  }

  private fun initRecyclerView() {
    adapter = RepoAdapter()
    binding.rvRepo.adapter = adapter
  }

  private fun initHeader() {
    val username = args.username
    viewModel.getUserDetail(username)
  }

  private fun initObserver() {
    viewModel.userDetail.observe(viewLifecycleOwner) { userDetail ->
      binding.tvFullName.text = userDetail.fullName.ifEmpty { "No Name" }
      binding.tvUsername.text = getString(R.string.username_placeholder, userDetail.username)
      if (userDetail.description.isNotEmpty()) {
        binding.tvDescription.text = userDetail.description
      } else {
        binding.tvDescription.visibility = View.GONE
      }

      Glide.with(binding.root)
        .load(userDetail.avatarUrl)
        .circleCrop()
        .into(binding.ivAvatar)

      adapter.submitList(userDetail.userRepos)
    }

    viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
      if (errorMessage.isNotEmpty()) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
        viewModel.resetError()
      }
    }

    viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
      binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}