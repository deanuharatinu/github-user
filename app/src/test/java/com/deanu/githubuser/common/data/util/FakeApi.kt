package com.deanu.githubuser.common.data.util

import com.deanu.githubuser.common.data.api.GithubUserApi
import com.deanu.githubuser.common.data.api.model.ApiGetDetailResponse
import com.deanu.githubuser.common.data.api.model.ApiGetRepoResponse
import com.deanu.githubuser.common.data.api.model.ApiSearchResponse
import com.haroldadmin.cnradapter.NetworkResponse
import org.mockito.Mockito
import retrofit2.Response

class FakeApi : GithubUserApi {
  override suspend fun searchGithubUser(username: String): NetworkResponse<ApiSearchResponse, ApiSearchResponse> {
    return when (username) {
      "success_not_empty" -> {
        val apiSearchResponse = ApiSearchResponse(
          1,
          false,
          DummyData.getDummyApiItems()
        )
        val response = Mockito.mock(Response::class.java)
        NetworkResponse.Success(apiSearchResponse, response)
      }
      "success_empty" -> {
        val apiSearchResponse = ApiSearchResponse(
          1,
          false,
          emptyList()
        )
        val response = Mockito.mock(Response::class.java)
        NetworkResponse.Success(apiSearchResponse, response)
      }
      else -> {
        val response = Mockito.mock(Response::class.java)
        NetworkResponse.ServerError(null, response)
      }
    }
  }

  override suspend fun getGithubUserDetail(username: String):
      NetworkResponse<ApiGetDetailResponse, ApiGetDetailResponse> {
    return if (username == "success" || username == "user_repos_failed") {
      val apiGetUserDesc = DummyData.getDummyUserDescription()
      val response = Mockito.mock(Response::class.java)
      NetworkResponse.Success(apiGetUserDesc, response)
    } else {
      val response = Mockito.mock(Response::class.java)
      NetworkResponse.ServerError(null, response)
    }
  }

  override suspend fun getGithubUserRepos(username: String):
      NetworkResponse<List<ApiGetRepoResponse>, List<ApiGetRepoResponse>> {
    return if (username == "success" || username == "user_desc_failed") {
      val apiGetGithubUserRepos = DummyData.getDummyUserRepos()
      val response = Mockito.mock(Response::class.java)
      NetworkResponse.Success(apiGetGithubUserRepos, response)
    } else {
      val response = Mockito.mock(Response::class.java)
      NetworkResponse.ServerError(null, response)
    }
  }
}