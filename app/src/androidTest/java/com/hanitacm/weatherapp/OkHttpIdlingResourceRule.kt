package com.hanitacm.weatherapp

import android.app.Application
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import javax.inject.Inject

class OkHttpIdlingResourceRule @Inject constructor(val idlingResource: IdlingResource) : TestRule {

  override fun apply(base: Statement?, description: Description?): Statement {

    return object : Statement() {
      override fun evaluate() {
//        Application.
//        //IdlingRegistry.getInstance().register(this@OkHttpIdlingResourceRule.idlingResource)
//        base?.evaluate()
//        IdlingRegistry.getInstance().unregister(idlingResource)
      }
    }
  }
}