package com.deanu.githubuser.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deanu.githubuser.common.utils.closeKeyboard
import com.deanu.githubuser.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private val viewModel: HomeViewModel by viewModels()
  private lateinit var adapter: UserAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initSearchBar()
    initRecyclerView()
    initObserver()
  }

  private fun initRecyclerView() {
    adapter = UserAdapter {}
    binding.rvUsers.adapter = adapter
  }

  private fun initObserver() {
    viewModel.userList.observe(viewLifecycleOwner) { userList ->
      if (userList.isNotEmpty()) {
        binding.rvUsers.visibility = View.VISIBLE
        binding.emptyPlaceholder.visibility = View.GONE
      } else {
        binding.rvUsers.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE
      }
      adapter.submitList(userList)
    }

    viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
      binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
      binding.rvUsers.visibility = if (isLoading) View.GONE else View.VISIBLE
      binding.emptyPlaceholder.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
      if (errorMessage.isNotEmpty()) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
        binding.rvUsers.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE
        viewModel.resetError()
      }
    }
  }

  private fun initSearchBar() {
    binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.searchUsername(query) }
        closeKeyboard(binding.root.context, binding.root)
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean = false
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}