package com.deanu.githubuser.common.data.api

import com.deanu.githubuser.common.data.api.model.ApiSearchResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubUserApi {
  @GET(ApiConstants.SEARCH_USER)
  suspend fun searchGithubUser(
    @Query(ApiConstants.SEARCH) username: String
  ): NetworkResponse<ApiSearchResponse, ApiSearchResponse>
}