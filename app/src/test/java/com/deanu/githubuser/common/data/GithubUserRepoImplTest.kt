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
  private val fakeCache = FakeCache()
  private val cache: Cache = fakeCache
  private lateinit var repository: GithubUserRepository

  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    repository = GithubUserRepoImpl(api, cache)
  }

  /*
   * TEST FOR SEARCH GITHUB USER
   */
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

  /*
   * TEST FOR GET USER DETAIL
   */
  @Test
  fun `when getUserDescription & getUserRepos success, UserDetailState should true and not null`() =
    runTest {
      // Given
      val username = "success"

      // When
      val response = repository.getUserDetail(username)

      // Then
      assertTrue(response.isSuccess)
      assertNotNull(response.userDetail)
    }

  @Test
  fun `when getUserDescription & getUserRepos success, should store to CacheUserDetail`() =
    runTest {
      // Given
      val expectedValue = DummyData.getDummyUserDescription()
      val username = "success"

      // When
      repository.getUserDetail(username)
      val actualValue = fakeCache.getUserDetail()

      // Then
      assertEquals(expectedValue.id.toString(), actualValue.id)
      assertEquals(expectedValue.username, actualValue.username)
      assertEquals(expectedValue.fullName, actualValue.fullName)
      assertEquals(expectedValue.avatarUrl, actualValue.avatarUrl)
      assertEquals(expectedValue.description, actualValue.description)
    }

  @Test
  fun `when getUserDescription & getUserRepos success, should store to CacheUserRepos`() = runTest {
    // Given
    val expectedValue = DummyData.getDummyUserRepos()[0]
    val username = "success"

    // When
    repository.getUserDetail(username)
    val actualValue = fakeCache.getUserRepos()[0]

    // Then
    assertEquals(expectedValue.id.toString(), actualValue.id)
    assertEquals(expectedValue.repoName, actualValue.repoName)
    assertEquals(expectedValue.repoDescription, actualValue.repoDescription)
    assertEquals(expectedValue.updatedAt, actualValue.updatedAt)
    assertEquals(expectedValue.stargazersCount, actualValue.stargazersCount)
  }

  @Test
  fun `when getUserDescription & getUserRepos success, repo list should have same size`() =
    runTest {
      // Given
      val expectedValue = 5
      val username = "success"

      // When
      val response = repository.getUserDetail(username)

      // Then
      assertEquals(response.userDetail?.userRepos?.size, expectedValue)
    }

  @Test
  fun `when getUserDescription failed & getUserRepos success, UserDetailState should false and null`() =
    runTest {
      // Given
      val username = "user_desc_failed"

      // When
      val response = repository.getUserDetail(username)

      // Then
      assertFalse(response.isSuccess)
      assertNull(response.userDetail)
    }

  @Test
  fun `when getUserDescription success & getUserRepos failed, UserDetailState should false and null`() =
    runTest {
      // Given
      val username = "user_repos_failed"

      // When
      val response = repository.getUserDetail(username)

      // Then
      assertFalse(response.isSuccess)
      assertNull(response.userDetail)
    }

  @Test
  fun `when getUserDescription failed & getUserRepos failed, UserDetailState should false and null`() =
    runTest {
      // Given
      val username = "failed"

      // When
      val response = repository.getUserDetail(username)

      // Then
      assertFalse(response.isSuccess)
      assertNull(response.userDetail)
    }
}