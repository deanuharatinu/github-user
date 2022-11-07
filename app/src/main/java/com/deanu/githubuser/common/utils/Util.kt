package com.deanu.githubuser.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun closeKeyboard(context: Context?, view: View) {
  val manager = ContextCompat.getSystemService(
    context!!,
    InputMethodManager::class.java
  )
  manager?.hideSoftInputFromWindow(view.windowToken, 0)
}