package com.deanu.githubuser.common.data.util

import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CacheUser
import com.deanu.githubuser.common.data.cache.model.CacheUserDetail
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos

class FakeCache : Cache {
  private var userList = emptyList<CacheUser>()
  override suspend fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser> {
    this@FakeCache.userList = userList
    return this@FakeCache.userList
  }

  override suspend fun insertAndGetUserRepos(userRepos: List<CacheUserRepos>): List<CacheUserRepos> {
    TODO("Not yet implemented")
  }

  override suspend fun insertAndGetUserDetail(userDetail: CacheUserDetail): CacheUserDetail {
    TODO("Not yet implemented")
  }
}