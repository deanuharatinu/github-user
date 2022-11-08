package com.deanu.githubuser.common.data.cache

import com.deanu.githubuser.common.data.cache.daos.GithubUserDao
import com.deanu.githubuser.common.data.cache.model.CacheUser
import javax.inject.Inject

class RoomCache @Inject constructor(
  private val githubUserDao: GithubUserDao
) : Cache {
  override suspend fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser> {
    return githubUserDao.insertAndGetUserList(userList)
  }
}