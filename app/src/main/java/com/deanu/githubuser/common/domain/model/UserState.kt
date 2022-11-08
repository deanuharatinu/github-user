package com.deanu.githubuser.common.domain.model

data class UserState(
  val isSuccess: Boolean,
  val userList: List<User>
)

data class UserDetailState(
  val isSuccess: Boolean,
  val userDetail: UserDetail?
)