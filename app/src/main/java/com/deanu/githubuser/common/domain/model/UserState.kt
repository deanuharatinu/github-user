package com.deanu.githubuser.common.domain.model

data class UserState(
  val isSuccess: Boolean,
  val userList: List<User>
)