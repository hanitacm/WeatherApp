package com.hanitacm.weatherapp

import android.app.Application
import com.hanitacm.weatherapp.di.AppComponent
import com.hanitacm.weatherapp.di.DaggerAppComponent

open class WeatherApplication : Application() {

  val mainComponent: AppComponent by lazy { initializeAppComponent() }

  open fun initializeAppComponent() = DaggerAppComponent.factory().create(applicationContext)
}