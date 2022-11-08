package com.deanu.githubuser.common.data.cache.daos

import androidx.room.*
import com.deanu.githubuser.common.data.cache.model.CacheUser

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
}