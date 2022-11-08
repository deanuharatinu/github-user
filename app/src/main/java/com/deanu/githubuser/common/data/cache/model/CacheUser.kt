package com.deanu.githubuser.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.model.UserRepo

@Entity(tableName = "user")
data class CacheUser(
  @PrimaryKey
  val id: String,
  val username: String,
  val avatarUrl: String
) {
  companion object {
    fun CacheUser.asDomain() = User(
      id = id,
      username = username,
      photoUrl = avatarUrl
    )
  }
}

@Entity(tableName = "user_detail")
data class CacheUserDetail(
  @PrimaryKey
  val id: String,
  val fullName: String,
  val username: String,
  val avatarUrl: String,
  val description: String,
)

@Entity(tableName = "user_repos")
data class CacheUserRepos(
  @PrimaryKey
  val id: String,
  val owner: String,
  val repoName: String,
  val repoDescription: String,
  val updatedAt: String,
  val stargazersCount: Int
) {
  companion object {
    fun CacheUserRepos.asDomain() = UserRepo(
      id = id,
      owner = owner,
      repoName = repoName,
      updatedAt = updatedAt,
      repoDescription = repoDescription,
      stargazersCount = stargazersCount
    )
  }
}