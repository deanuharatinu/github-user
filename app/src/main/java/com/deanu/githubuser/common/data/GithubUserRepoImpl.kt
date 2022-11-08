package com.deanu.githubuser.common.data

import com.deanu.githubuser.common.data.api.GithubUserApi
import com.deanu.githubuser.common.data.api.model.ApiItems.Companion.asCache
import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CacheUser.Companion.asDomain
import com.deanu.githubuser.common.domain.model.UserState
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class GithubUserRepoImpl @Inject constructor(
  private val api: GithubUserApi,
  private val cache: Cache
) : GithubUserRepository {
  override suspend fun searchUser(username: String): UserState {
    return when (val response = api.searchGithubUser(username)) {
      is NetworkResponse.Success -> {
        val cacheUserList = response.body.items?.map { it.asCache() }
        val userList = cache.insertAndGetUserList(cacheUserList.orEmpty()).map { it.asDomain() }
        UserState(true, userList)
      }
      is NetworkResponse.Error -> {
        UserState(false, emptyList())
      }
    }
  }
}