package com.deanu.githubuser.common.data.cache

import com.deanu.githubuser.common.data.cache.model.CacheUser

interface Cache {
  suspend fun insertAndGetUserList(userList: List<CacheUser>):List<CacheUser>
}