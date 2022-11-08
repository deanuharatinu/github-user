package com.deanu.githubuser.common.domain.model

data class User(
  val id: String,
  val username: String,
  val photoUrl: String?
)

data class UserDetail(
  val id: String,
  val fullName: String,
  val username: String,
  val avatarUrl: String,
  val description: String,
  val userRepos: List<UserRepo>
)

data class UserRepo(
  val id: String,
  val owner: String,
  val repoName: String,
  val repoDescription: String,
  val updatedAt: String,
  val stargazersCount: Int
)