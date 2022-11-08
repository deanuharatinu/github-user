package com.deanu.githubuser.common.data.util

import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CacheUser

class FakeCache : Cache {
  var userList = emptyList<CacheUser>()
  override suspend fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser> {
    this@FakeCache.userList = userList
    return this@FakeCache.userList
  }
}