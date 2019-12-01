package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import com.hanitacm.weatherapp.repository.data.WeatherLocationData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

  @GET("weather")
  fun getWeatherData(@Query("APPID") api_key: String, @Query("q") location: String): Single<WeatherData>

  @GET("find?type=like&sort=population&cnt=30")
  fun getWeatherLocation(@Query("APPID") api_key: String, @Query("q") location: String):Single<WeatherLocationData>
}