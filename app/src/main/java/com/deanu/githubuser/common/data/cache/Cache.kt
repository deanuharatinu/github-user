package com.deanu.githubuser.common.data.cache

import com.deanu.githubuser.common.data.cache.model.CacheUser
import com.deanu.githubuser.common.data.cache.model.CacheUserDetail
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos

interface Cache {
  suspend fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser>
  suspend fun insertAndGetUserRepos(userRepos: List<CacheUserRepos>): List<CacheUserRepos>
  suspend fun insertAndGetUserDetail(userDetail: CacheUserDetail): CacheUserDetail
}