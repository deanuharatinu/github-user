package com.deanu.githubuser.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deanu.githubuser.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
  private var _binding: FragmentDetailBinding? = null
  private val binding get() = _binding!!
  private val viewModel: DetailViewModel by viewModels()
  val args: DetailFragmentArgs by navArgs()

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
    initObserver()
  }

  private fun initHeader() {
    val username = args.username
    viewModel.getUserDetail(username)
  }

  private fun initObserver() {
    viewModel.userDetail.observe(viewLifecycleOwner) { userDetail ->
      binding.tvFullName.text = userDetail.fullName
      binding.tvUsername.text = "@${userDetail.username}"
      binding.tvDescription.text = userDetail.description

      Glide.with(binding.root)
        .load(userDetail.avatarUrl)
        .circleCrop()
        .into(binding.ivAvatar)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}