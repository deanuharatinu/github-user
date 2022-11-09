package com.deanu.githubuser.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.deanu.githubuser.R
import com.deanu.githubuser.common.data.api.GithubUserApi
import com.deanu.githubuser.common.data.di.ApiModule
import com.deanu.githubuser.common.data.utils.*
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@LargeTest
@UninstallModules(ApiModule::class)
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
  @Inject
  lateinit var okHttpClient: OkHttpClient
  private val fakeServer = FakeServer()

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Module
  @InstallIn(SingletonComponent::class)
  object TestApiModule {
    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): GithubUserApi {
      return builder.build().create(GithubUserApi::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
      return Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .client(okHttpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
      return OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
          level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    }
  }

  private lateinit var okHttp3IdlingResource: OkHttp3IdlingResource

  @Before
  fun setup() {
    hiltRule.inject()
    okHttp3IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
    IdlingRegistry.getInstance().register(okHttp3IdlingResource)
    fakeServer.start()
  }

  @After
  fun tearDown() {
    fakeServer.shutdown()
    IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
  }

  @Test
  fun homeFragmentOnFirstTime_shouldDisplayEmptyPlaceholder() {
    // When
    launchFragmentInHiltContainer<HomeFragment> {}

    // Then
    onView(withId(R.id.empty_placeholder)).check(matches(isDisplayed()))
  }

  @Test
  fun whenSearchAndFound_shouldDisplayUserAsExpected() {
    // Given
    val expectedUsername = "skydoves"
    fakeServer.setHappyPathDispatcher()

    // When
    launchFragmentInHiltContainer<HomeFragment> { }
    onView(withId(androidx.appcompat.R.id.search_src_text))
      .perform(typeText(expectedUsername), pressImeActionButton())
    onView(isRoot()).perform(waitFor(500))

    // Then
    onView(withId(R.id.rv_users))
      .check(matches(atPosition(0, hasDescendant(withText(expectedUsername)))))
  }

  @Test
  fun whenNetworkError_shouldDisplaySnackBarNetworkError() {
    // Given
    val username = "error"
    fakeServer.setHappyPathDispatcher()

    // When
    launchFragmentInHiltContainer<HomeFragment> { }
    onView(withId(androidx.appcompat.R.id.search_src_text))
      .perform(typeText(username), pressImeActionButton())

    // Then
    onView(withId(com.google.android.material.R.id.snackbar_text))
      .check(matches(withText("Network Error")))
  }
}