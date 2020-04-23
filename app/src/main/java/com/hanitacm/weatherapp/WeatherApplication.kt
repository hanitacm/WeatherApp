package com.hanitacm.weatherapp

import android.app.Application
import com.hanitacm.weatherapp.di.AppComponent
import com.hanitacm.weatherapp.di.DaggerAppComponent

class WeatherApplication : Application() {

  val mainComponent: AppComponent by lazy { DaggerAppComponent.factory().create(applicationContext) }
}