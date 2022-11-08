package com.deanu.githubuser.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.model.UserState
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private val repository = mock(GithubUserRepository::class.java)
  private lateinit var homeViewModel: HomeViewModel

  @Before
  fun setup() {
    val dispatcherProvider = object : DispatchersProvider {
      override fun io(): CoroutineDispatcher = testCoroutineRule.testDispatcher
    }
    homeViewModel = HomeViewModel(repository, dispatcherProvider)
  }

  @Test
  fun `when success and no user found, userList should empty`() = runTest {
    // Given
    val expectedValue = emptyList<User>()
    val userState = UserState(true, expectedValue)

    // When
    `when`(repository.searchUser("michael")).thenReturn(userState)
    homeViewModel.searchUsername("michael")
    val actualValue = homeViewModel.userList.getOrAwaitValue()
    val isLoadingValue = homeViewModel.isLoading.getOrAwaitValue()

    // Then
    verify(repository).searchUser("michael")
    assertFalse(isLoadingValue)
    assertEquals(expectedValue, actualValue)
  }

  @Test
  fun `when success and users found, userList should not empty`() = runTest {
    // Given
    val expectedValue = listOf(User("id123", "michael", "http://image"))
    val userState = UserState(true, expectedValue)

    // When
    `when`(repository.searchUser("michael")).thenReturn(userState)
    homeViewModel.searchUsername("michael")
    val actualValue = homeViewModel.userList.getOrAwaitValue()
    val isLoadingValue = homeViewModel.isLoading.getOrAwaitValue()

    // Then
    verify(repository).searchUser("michael")
    assertFalse(isLoadingValue)
    assertEquals(expectedValue, actualValue)
  }

  @Test
  fun `when not success, errorMessage value should as expected`() = runTest {
    // Given
    val expectedValue = "Network Error"
    val userState = UserState(false, emptyList())

    // When
    `when`(repository.searchUser("michael")).thenReturn(userState)
    homeViewModel.searchUsername("michael")
    val actualValue = homeViewModel.errorMessage.getOrAwaitValue()
    val isLoadingValue = homeViewModel.isLoading.getOrAwaitValue()

    // Then
    verify(repository).searchUser("michael")
    assertFalse(isLoadingValue)
    assertEquals(expectedValue, actualValue)
  }

  @Test
  fun `when resetError, errorMessage should empty string`() = runTest {
    // Given
    val expectedValueBeforeReset = "Network Error"
    val expectedValueAfterReset = ""
    val userState = UserState(false, emptyList())

    // When
    `when`(repository.searchUser("michael")).thenReturn(userState)
    homeViewModel.searchUsername("michael")
    val valueBeforeReset = homeViewModel.errorMessage.getOrAwaitValue()
    homeViewModel.resetError()
    val valueAfterReset = homeViewModel.errorMessage.getOrAwaitValue()

    // Then
    assertEquals(expectedValueBeforeReset, valueBeforeReset)
    assertEquals(expectedValueAfterReset, valueAfterReset)
  }
}