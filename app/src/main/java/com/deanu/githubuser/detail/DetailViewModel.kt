package com.deanu.githubuser.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.deanu.githubuser.common.utils.coroutine.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val repository: GithubUserRepository,
  private val dispatchersProvider: DispatchersProvider
) : ViewModel() {
  private val _isLoading = MutableLiveData(false)
  val isLoading: LiveData<Boolean> = _isLoading

  private val _userDetail = MutableLiveData<UserDetail>()
  val userDetail: LiveData<UserDetail> = _userDetail

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = _errorMessage

  fun getUserDetail(username: String) {
    _isLoading.postValue(true)
    viewModelScope.launch(dispatchersProvider.io()) {
      val userDetailState = repository.getUserDetail(username)

      when (userDetailState.isSuccess) {
        true -> {
          _isLoading.postValue(false)
          userDetailState.userDetail?.let { _userDetail.postValue(it) }
        }
        false -> {
          _isLoading.postValue(false)
          _errorMessage.postValue("Network Error")
        }
      }
    }
  }
}