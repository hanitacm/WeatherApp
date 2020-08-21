package com.hanitacm.weatherapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.hanitacm.weatherapp.presentation.screen.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

  @get:Rule
  val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

  @Test
  fun useAppContext() {
    activityTestRule.launchActivity(null)
  }
}
