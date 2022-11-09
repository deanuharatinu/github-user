package com.deanu.githubuser.common.data.utils

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStream

class FakeServer {
  private val mockWebServer = MockWebServer()
  private val notFoundResponse = MockResponse().setResponseCode(404)

  fun start() {
    mockWebServer.start(8080)
  }

  fun setHappyPathDispatcher() {
    mockWebServer.dispatcher = object : Dispatcher() {
      override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return notFoundResponse

        return with(path) {
          when {
            contains("skydoves", true) -> {
              MockResponse()
                .setResponseCode(200)
                .setBody(getJson("search_response.json"))
            }
            contains("error", true) -> notFoundResponse
            else -> notFoundResponse
          }
        }
      }
    }
  }

  fun shutdown() {
    mockWebServer.shutdown()
  }

  private fun getJson(path: String): String {
    return try {
      val context = InstrumentationRegistry.getInstrumentation().context
      val jsonStream: InputStream = context.assets.open(path)
      String(jsonStream.readBytes())
    } catch (exception: IOException) {
      throw exception
    }
  }
}