package com.deanu.githubuser.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deanu.githubuser.common.data.cache.daos.GithubUserDao
import com.deanu.githubuser.common.data.cache.model.CacheUser

@Database(
  entities = [CacheUser::class],
  version = 1,
  exportSchema = true
)
abstract class GithubUserDatabase : RoomDatabase() {
  abstract fun githubUserDao(): GithubUserDao
}