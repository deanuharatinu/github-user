package com.deanu.githubuser.common.domain.repository

import com.deanu.githubuser.common.domain.model.UserDetailState
import com.deanu.githubuser.common.domain.model.UserState

interface GithubUserRepository {
  suspend fun searchUser(username: String): UserState
  suspend fun getUserDetail(username: String): UserDetailState
}