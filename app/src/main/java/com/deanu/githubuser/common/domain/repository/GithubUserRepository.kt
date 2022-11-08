package com.deanu.githubuser.common.domain.repository

import com.deanu.githubuser.common.domain.model.UserState

interface GithubUserRepository {
  suspend fun searchUser(username: String): UserState
}