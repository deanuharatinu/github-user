package com.deanu.githubuser.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deanu.githubuser.common.data.api.GithubUserApi
import com.deanu.githubuser.common.data.api.model.ApiItems.Companion.asCache
import com.deanu.githubuser.common.data.cache.Cache
import com.deanu.githubuser.common.data.cache.model.CacheUser.Companion.asDomain
import com.deanu.githubuser.common.data.util.DummyData
import com.deanu.githubuser.common.data.util.FakeApi
import com.deanu.githubuser.common.data.util.FakeCache
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GithubUserRepoImplTest {
  private val api: GithubUserApi = FakeApi()
  private val cache: Cache = FakeCache()
  private lateinit var repository: GithubUserRepository

  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    repository = GithubUserRepoImpl(api, cache)
  }

  @Test
  fun `when response is success with empty user list, UserState should true and empty`() = runTest {
    // Given
    val username = "success_empty"
    val expectedUserList = emptyList<User>()

    // When
    val response = repository.searchUser(username)

    // Then
    assertTrue(response.isSuccess)
    assertEquals(expectedUserList, response.userList)
  }

  @Test
  fun `when response is success with user list, UserState should true with user list`() = runTest {
    // Given
    val username = "success_not_empty"
    val expectedUserList = DummyData
      .getDummyApiItems()
      .map { it.asCache() }
      .map { it.asDomain() }

    // When
    val response = repository.searchUser(username)

    // Then
    assertTrue(response.isSuccess)
    assertEquals(expectedUserList, response.userList)
  }

  @Test
  fun `when response is not success, UserState should false with empty list`() = runTest {
    // Given
    val username = "not_success"
    val expectedUserList = emptyList<User>()

    // When
    val response = repository.searchUser(username)

    // Then
    assertFalse(response.isSuccess)
    assertEquals(expectedUserList, response.userList)
  }
}