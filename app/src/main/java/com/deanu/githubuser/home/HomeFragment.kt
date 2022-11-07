package com.deanu.githubuser.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import com.deanu.githubuser.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private lateinit var viewModel: HomeViewModel

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
    initSearchResult()
  }

  private fun initSearchResult() {

//    binding.rvUsers.adapter =
  }

  private fun initSearchBar() {
    binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        Toast.makeText(binding.root.context, query.orEmpty(), Toast.LENGTH_SHORT).show()
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