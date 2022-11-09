package com.deanu.githubuser.common.data.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
  return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description) {
      description.appendText("has item at position $position: ")
      itemMatcher.describeTo(description)
    }

    override fun matchesSafely(view: RecyclerView): Boolean {
      val viewHolder = view.findViewHolderForAdapterPosition(position)
        ?: // has no item on such position
        return false
      return itemMatcher.matches(viewHolder.itemView)
    }
  }
}

fun waitFor(delay: Long): ViewAction {
  return object : ViewAction {
    override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
    override fun getDescription(): String = "wait for $delay milliseconds"
    override fun perform(uiController: UiController, v: View?) {
      uiController.loopMainThreadForAtLeast(delay)
    }
  }
}