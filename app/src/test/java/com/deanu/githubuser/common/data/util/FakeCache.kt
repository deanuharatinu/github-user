package com.deanu.githubuser.common.data.util

import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CacheUser
import com.deanu.githubuser.common.data.cache.model.CacheUserDetail
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos

class FakeCache : Cache {
  private var userList = emptyList<CacheUser>()
  private var userRepos = emptyList<CacheUserRepos>()
  private lateinit var userDetail: CacheUserDetail

  override suspend fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser> {
    this@FakeCache.userList = userList
    return this@FakeCache.userList
  }

  override suspend fun insertAndGetUserRepos(userRepos: List<CacheUserRepos>): List<CacheUserRepos> {
    this@FakeCache.userRepos = userRepos
    return this@FakeCache.userRepos
  }

  override suspend fun insertAndGetUserDetail(userDetail: CacheUserDetail): CacheUserDetail {
    this@FakeCache.userDetail = userDetail
    return this@FakeCache.userDetail
  }

  fun getUserDetail(): CacheUserDetail = userDetail
  fun getUserRepos(): List<CacheUserRepos> = userRepos
}