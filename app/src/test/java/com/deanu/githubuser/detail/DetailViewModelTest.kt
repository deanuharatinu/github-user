package com.deanu.githubuser.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deanu.githubuser.common.data.api.model.ApiGetRepoResponse.Companion.asCache
import com.deanu.githubuser.common.data.cache.model.CacheUserRepos.Companion.asDomain
import com.deanu.githubuser.common.data.util.DummyData
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.model.UserDetailState
import com.deanu.githubuser.common.domain.model.UserRepo
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.deanu.githubuser.common.utils.coroutine.DispatchersProvider
import com.deanu.githubuser.testutil.TestCoroutineRule
import com.deanu.githubuser.testutil.getOrAwaitValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private val repository = Mockito.mock(GithubUserRepository::class.java)
  private lateinit var detailViewModel: DetailViewModel

  @Before
  fun setup() {
    val dispatcherProvider = object : DispatchersProvider {
      override fun io(): CoroutineDispatcher = testCoroutineRule.testDispatcher
    }
    detailViewModel = DetailViewModel(repository, dispatcherProvider)
  }

  @Test
  fun `when getUserDetail success, userDetail is not null & equal to expected value`() =
    runTest {
      // Given
      val userDesc = DummyData.getDummyUserDescription()
      val cacheRepos = DummyData.getDummyUserRepos().map { apiGetRepo ->
        userDesc.username?.let { username ->
          apiGetRepo.asCache(username)
        }
      }
      val userRepos = cacheRepos.map {
        it?.asDomain() ?: UserRepo(
          id = "1",
          owner = "Project 1 lorem ipsum dolor sit amet",
          repoName = "Repo Name",
          repoDescription = "A personal project number 1",
          updatedAt = "2022-11-07T12:20:56Z",
          stargazersCount = 1
        )
      }

      val userDetail = UserDetail(
        id = userDesc.id.toString(),
        fullName = userDesc.fullName.orEmpty(),
        username = userDesc.username.orEmpty(),
        avatarUrl = userDesc.avatarUrl.orEmpty(),
        description = userDesc.description.orEmpty(),
        userRepos = userRepos
      )

      // When
      `when`(repository.getUserDetail("michael")).thenReturn(
        UserDetailState(true, userDetail)
      )
      detailViewModel.getUserDetail("michael")
      val actualLoadingValue = detailViewModel.isLoading.getOrAwaitValue()
      val actualUserDetailValue = detailViewModel.userDetail.getOrAwaitValue()

      // Then
      assertNotNull(actualUserDetailValue)
      assertFalse(actualLoadingValue)
      assertEquals(5, actualUserDetailValue.userRepos.size)
      assertEquals(userDetail.id, actualUserDetailValue.id)
      assertEquals(userDetail.username, actualUserDetailValue.username)
    }

  @Test
  fun `when getUserDetail is failed, errorMessage should be Network Error`() =
    runTest {
      // Given
      val expectedValue = "Network Error"

      // When
      `when`(repository.getUserDetail("Michael"))
        .thenReturn(UserDetailState(false, null))
      detailViewModel.getUserDetail("Michael")
      val actualErrorValue = detailViewModel.errorMessage.getOrAwaitValue()

      // Then
      assertEquals(expectedValue, actualErrorValue)
    }
}