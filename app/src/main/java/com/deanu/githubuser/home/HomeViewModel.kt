package com.deanu.githubuser.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.deanu.githubuser.common.utils.coroutine.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val repository: GithubUserRepository,
  private val dispatchersProvider: DispatchersProvider

) : ViewModel() {
  private val _isLoading = MutableLiveData(false)
  val isLoading: LiveData<Boolean> = _isLoading

  private val _userList = MutableLiveData<List<User>>()
  val userList: LiveData<List<User>> = _userList

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = _errorMessage

  fun searchUsername(username: String) {
    _isLoading.value = true
    viewModelScope.launch(dispatchersProvider.io()) {
      val userState = repository.searchUser(username)
      _isLoading.postValue(false)
      _userList.postValue(userState.userList)
      if (!userState.isSuccess) {
        _errorMessage.postValue("Network Error")
      }
    }
  }

  fun resetError() {
    _errorMessage.value = ""
  }
}