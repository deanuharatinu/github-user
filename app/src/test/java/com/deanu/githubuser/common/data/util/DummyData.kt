package com.deanu.githubuser.common.data.util

import com.deanu.githubuser.common.data.api.model.ApiGetDetailResponse
import com.deanu.githubuser.common.data.api.model.ApiGetRepoResponse
import com.deanu.githubuser.common.data.api.model.ApiItems

object DummyData {
  fun getDummyApiItems(): List<ApiItems> {
    return listOf(ApiItems(1234, "michael", "http://image"))
  }

  fun getDummyUserDescription(): ApiGetDetailResponse {
    return ApiGetDetailResponse(
      id = 123,
      username = "michaeljordan",
      fullName = "Michael Jordan",
      avatarUrl = "http://",
      description = "Halo this is Michael Jordan"
    )
  }

  fun getDummyUserRepos(): List<ApiGetRepoResponse> {
    val userRepos = arrayListOf<ApiGetRepoResponse>()
    for (i in 1..5) {
      val apiGetRepoResponse = ApiGetRepoResponse(
        id = i,
        "Project $i lorem ipsum dolor sit amet",
        repoDescription = "A personal project number $i",
        updatedAt = "2022-11-07T12:20:56Z",
        stargazersCount = i
      )
      userRepos.add(apiGetRepoResponse)
    }
    return userRepos
  }
}