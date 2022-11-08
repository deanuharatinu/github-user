package com.deanu.githubuser.common.data.api

import com.deanu.githubuser.common.data.api.model.ApiGetDetailResponse
import com.deanu.githubuser.common.data.api.model.ApiGetRepoResponse
import com.deanu.githubuser.common.data.api.model.ApiSearchResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {
  @GET(ApiConstants.SEARCH_USER)
  suspend fun searchGithubUser(
    @Query(ApiConstants.SEARCH_QUERY) username: String
  ): NetworkResponse<ApiSearchResponse, ApiSearchResponse>

  @GET(ApiConstants.GET_USER_DETAIL + "/{username}")
  suspend fun getGithubHUserDetail(
    @Path("username") username: String
  ): NetworkResponse<ApiGetDetailResponse, ApiGetDetailResponse>

  @GET(ApiConstants.GET_USER_DETAIL + "/{username}/" + ApiConstants.GET_USER_REPO)
  suspend fun getGithubUserRepos(
    @Path("username") username: String
  ): NetworkResponse<List<ApiGetRepoResponse>, List<ApiGetRepoResponse>>
}