package com.hanitacm.weatherapp

import com.hanitacm.weatherapp.di.AppComponent
import com.hanitacm.weatherapp.di.DaggerTestAppComponent

class TestApplication : WeatherApplication() {
  override fun initializeAppComponent(): AppComponent {
    return DaggerTestAppComponent.create()
  }
}
