package com.deanu.githubuser.common.data.api.model

import com.deanu.githubuser.common.data.cache.model.CacheUser
import com.google.gson.annotations.SerializedName

data class ApiSearchResponse(
  @field:SerializedName("total_count")
  val totalCount: Int = 0,
  @field:SerializedName("incomplete_results")
  val incompleteResults: Boolean? = null,
  @field:SerializedName("items")
  val items: List<ApiItems>? = null
)

data class ApiItems(
  @field:SerializedName("id")
  val id: Int? = null,
  @field:SerializedName("login")
  val username: String? = null,
  @field:SerializedName("avatar_url")
  val avatarUrl: String? = null
) {
  companion object {
    fun ApiItems.asCache() = CacheUser(
      id = id.toString(),
      username = username.orEmpty(),
      avatarUrl = avatarUrl.orEmpty()
    )
  }
}
