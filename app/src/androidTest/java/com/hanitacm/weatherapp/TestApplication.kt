package com.hanitacm.weatherapp

import com.hanitacm.weatherapp.di.AppComponent
import com.hanitacm.weatherapp.di.DaggerTestAppComponent
import com.hanitacm.weatherapp.di.TestAppComponent


class TestApplication : WeatherApplication() {
  val testComponent: AppComponent by lazy { initializeAppComponent() }


  override fun initializeAppComponent(): AppComponent {
    return DaggerTestAppComponent.create()
  }
}
