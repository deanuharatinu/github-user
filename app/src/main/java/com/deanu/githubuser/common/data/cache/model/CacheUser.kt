package com.deanu.githubuser.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deanu.githubuser.common.domain.model.User

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