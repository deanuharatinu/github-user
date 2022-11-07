package com.deanu.githubuser.common.domain.repository

import com.deanu.githubuser.common.data.api.model.ApiSearchResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface GithubUserRepository {
  suspend fun searchUser(username: String): NetworkResponse<ApiSearchResponse, ApiSearchResponse>
}