package com.deanu.githubuser.home

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.deanu.githubuser.R
import com.deanu.githubuser.common.data.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun homeFragmentOnFirstTime_shouldDisplayEmptyPlaceholder() {
    launchFragmentInHiltContainer<HomeFragment> {}

    onView(withId(R.id.empty_placeholder)).check(matches(isDisplayed()))
  }

  @Test
  fun whenSearchAndFound_shouldDisplayUserAsExpected() {
    launchFragmentInHiltContainer<HomeFragment> { }

    onView(withId(androidx.appcompat.R.id.search_src_text))
      .perform(typeText("skydoves"), pressImeActionButton())
    onView(isRoot()).perform(waitFor(60000))
  }

  private fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
      override fun getConstraints(): Matcher<View> = isRoot()
      override fun getDescription(): String = "wait for $delay milliseconds"
      override fun perform(uiController: UiController, v: View?) {
        uiController.loopMainThreadForAtLeast(delay)
      }
    }
  }

}