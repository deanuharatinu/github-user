package com.deanu.githubuser.common.data

import com.deanu.githubuser.common.data.api.GithubUserApi
import com.deanu.githubuser.common.data.api.model.ApiSearchResponse
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class GithubUserRepoImpl @Inject constructor(
  private val api: GithubUserApi
) : GithubUserRepository {
  override suspend fun searchUser(username: String)
      : NetworkResponse<ApiSearchResponse, ApiSearchResponse> {
    return api.getGithubUserList(username)
  }
}