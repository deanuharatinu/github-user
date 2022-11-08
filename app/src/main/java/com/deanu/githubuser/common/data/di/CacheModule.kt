package com.deanu.githubuser.common.data.di

import android.content.Context
import androidx.room.Room
import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.GithubUserDatabase
import com.deanu.githubuser.common.data.cache.RoomCache
import com.deanu.githubuser.common.data.cache.daos.GithubUserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {
  @Binds
  abstract fun bindCache(cache: RoomCache): Cache

  companion object {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GithubUserDatabase {
      return Room.databaseBuilder(
        context,
        GithubUserDatabase::class.java,
        "GITHUB_USER_DB"
      ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideGithubUserDao(database: GithubUserDatabase): GithubUserDao {
      return database.githubUserDao()
    }
  }
}