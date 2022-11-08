package com.deanu.githubuser.common.data.api.model

import com.deanu.githubuser.common.domain.model.UserRepo
import com.google.gson.annotations.SerializedName

data class ApiGetDetailResponse(
  @field:SerializedName("id")
  val id: Int? = null,
  @field:SerializedName("login")
  val username: String? = null,
  @field:SerializedName("name")
  val fullName: String? = null,
  @field:SerializedName("avatar_url")
  val avatarUrl: String? = null,
  @field:SerializedName("bio")
  val description: String? = null,
)

data class ApiGetRepoResponse(
  @field:SerializedName("id")
  val id: Int? = null,
  @field:SerializedName("name")
  val repoName: String? = null,
  @field:SerializedName("description")
  val repoDescription: String? = null,
  @field:SerializedName("updated_at")
  val updatedAt: String? = null,
  @field:SerializedName("stargazers_count")
  val stargazersCount: Int? = null
) {
  companion object {
    fun ApiGetRepoResponse.asDomain(username: String) = UserRepo(
      id = id.toString(),
      owner = username,
      repoName = repoName.orEmpty(),
      repoDescription = repoDescription.orEmpty(),
      updatedAt = updatedAt.orEmpty(),
      stargazersCount = stargazersCount ?: 0
    )
  }
}