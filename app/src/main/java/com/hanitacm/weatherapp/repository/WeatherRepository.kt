package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.repository.api.WeatherService
import retrofit2.Retrofit

class WeatherRepository {
  private val weatherAPIUrl = "http://api.openweathermap.org/data/2.5/"

  fun getWeather(location:String){
    val retrofit = Retrofit.Builder()
        .baseUrl(weatherAPIUrl)
        .build()

    val service = retrofit.create(WeatherService::class.java)

    service.getWeatherData(location)
  }


}
