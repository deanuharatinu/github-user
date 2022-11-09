package com.deanu.githubuser.common.data

import com.deanu.githubuser.common.data.api.GithubUserApi
import com.deanu.githubuser.common.data.api.model.ApiGetDetailResponse.Companion.asCache
import com.deanu.githubuser.common.data.api.model.ApiGetRepoResponse.Companion.asCache
import com.deanu.githubuser.common.data.api.model.ApiItems.Companion.asCache
import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CacheUser.Companion.asDomain
import com.deanu.githubuser.common.data.cache.model.CacheUserDetail
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos.Companion.asDomain
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.model.UserDetailState
import com.deanu.githubuser.common.domain.model.UserRepo
import com.deanu.githubuser.common.domain.model.UserState
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

  override suspend fun getUserDetail(username: String): UserDetailState {
    val userDetailState = coroutineScope {
      val userDescription = async { getUserDescription(username) }
      val userRepos = async { getUserRepos(username) }
      combineDetail(userDescription, userRepos)
    }
    return userDetailState
  }

  private suspend fun getUserDescription(username: String): CacheUserDetail? {
    return when (val response = api.getGithubUserDetail(username)) {
      is NetworkResponse.Success -> {
        val cacheUserDetail = response.body
        cache.insertAndGetUserDetail(cacheUserDetail.asCache())
      }
      is NetworkResponse.Error -> null
    }
  }

  private suspend fun getUserRepos(username: String): List<UserRepo>? {
    return when (val response = api.getGithubUserRepos(username)) {
      is NetworkResponse.Success -> {
        val cacheUserRepo = response.body.map { it.asCache(username) }
        cache.insertAndGetUserRepos(cacheUserRepo).map { it.asDomain() }
      }
      is NetworkResponse.Error -> null
    }
  }

  private suspend fun combineDetail(
    userDescription: Deferred<CacheUserDetail?>,
    userRepos: Deferred<List<UserRepo>?>
  ): UserDetailState {
    val userRepo = userRepos.await()
    val isRepoComplete = when (userRepo) {
      null -> false
      else -> true
    }

    val userDetail = userDescription.await()
    val isDetailComplete = when (userDetail) {
      null -> false
      else -> true
    }

    return if (isDetailComplete && isRepoComplete) {
      val user = UserDetail(
        userDetail?.id.toString(),
        userDetail?.fullName.orEmpty(),
        userDetail?.username.orEmpty(),
        userDetail?.avatarUrl.orEmpty(),
        userDetail?.description.orEmpty(),
        userRepo.orEmpty()
      )
      UserDetailState(true, user)
    } else {
      UserDetailState(false, null)
    }
  }
}