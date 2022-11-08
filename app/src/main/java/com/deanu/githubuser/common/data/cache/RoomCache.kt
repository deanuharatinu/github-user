package com.deanu.githubuser.common.data.cache

import com.deanu.githubuser.common.data.cache.daos.GithubUserDao
import com.deanu.githubuser.common.data.cache.model.CacheUser
import com.deanu.githubuser.common.data.cache.model.CacheUserDetail
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos
import javax.inject.Inject

class RoomCache @Inject constructor(
  private val githubUserDao: GithubUserDao
) : Cache {
  override suspend fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser> {
    return githubUserDao.insertAndGetUserList(userList)
  }

  override suspend fun insertAndGetUserRepos(userRepos: List<CacheUserRepos>): List<CacheUserRepos> {
    return githubUserDao.insertAndGetUserRepos(userRepos)
  }

  override suspend fun insertAndGetUserDetail(userDetail: CacheUserDetail): CacheUserDetail {
    return githubUserDao.insertAndGetUserDetail(userDetail)
  }
}