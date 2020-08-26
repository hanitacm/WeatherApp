package com.hanitacm.weatherapp.utils


import androidx.test.platform.app.InstrumentationRegistry
import com.hanitacm.weatherapp.TestApplication
import okhttp3.mockwebserver.MockResponse
import java.io.IOException
import java.io.InputStreamReader

fun MockResponse.fromJson(jsonFile: String): MockResponse =
    setBody(readStringFromFile(jsonFile))


private fun readStringFromFile(fileName: String): String {
  try {
    val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(fileName)
    val builder = StringBuilder()
    val reader = InputStreamReader(inputStream, "UTF-8")
    reader.readLines().forEach {
      builder.append(it)
    }
    return builder.toString()
  } catch (e: IOException) {
    throw e
  }
}

