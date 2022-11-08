package com.deanu.githubuser.common.data.cache.daos

import androidx.room.*
import com.deanu.githubuser.common.data.cache.model.CacheUser
import com.deanu.githubuser.common.data.cache.model.CacheUserDetail
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos

@Dao
interface GithubUserDao {
  @Insert
  fun insertUserList(userList: List<CacheUser>)

  @Query("SELECT * FROM user")
  fun getUserList(): List<CacheUser>

  @Query("DELETE FROM user")
  fun deleteUserList()

  @Transaction
  fun insertAndGetUserList(userList: List<CacheUser>): List<CacheUser> {
    deleteUserList()
    insertUserList(userList)
    return getUserList()
  }

  @Insert
  fun insertUserRepos(userRepos: List<CacheUserRepos>)

  @Query("SELECT * FROM user_repos")
  fun getUserRepos(): List<CacheUserRepos>

  @Query("DELETE FROM user_repos")
  fun deleteUserRepos()

  @Transaction
  fun insertAndGetUserRepos(userRepos: List<CacheUserRepos>): List<CacheUserRepos> {
    deleteUserRepos()
    insertUserRepos(userRepos)
    return getUserRepos()
  }

  @Insert
  fun insertUserDetail(userDetail: CacheUserDetail)

  @Query("SELECT * FROM user_detail WHERE username = :username")
  fun getUserDetail(username: String): CacheUserDetail

  @Query("DELETE FROM user_detail")
  fun deleteUserDetail()

  @Transaction
  fun insertAndGetUserDetail(userDetail: CacheUserDetail): CacheUserDetail {
    deleteUserDetail()
    insertUserDetail(userDetail)
    return getUserDetail(userDetail.username)
  }
}