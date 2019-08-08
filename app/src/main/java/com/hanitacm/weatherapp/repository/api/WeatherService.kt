package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

  @GET("weather")
  fun getWeatherData(@Query("q") location: String): WeatherData
}