package com.deanu.githubuser.common.data.util

import com.deanu.githubuser.common.data.api.model.ApiItems

object DummyData {
  fun getDummyApiItems(): List<ApiItems> {
    return listOf(ApiItems(1234, "michael", "http://image"))
  }
}