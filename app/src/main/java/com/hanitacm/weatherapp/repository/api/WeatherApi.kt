package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import com.hanitacm.weatherapp.repository.data.WeatherLocationData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApi @Inject constructor(private val weatherService: WeatherService) {
  private val apiKey = "f33ec8d7f07ee949745b53d8474dbeb2"


  fun getWeather(location: String): Single<WeatherData> = weatherService.getWeatherData(apiKey, location)

  fun getWeather(latitude: String, longitude: String): Single<WeatherData> = weatherService.getWeatherData(apiKey, latitude, longitude)

  fun getWeatherLocations(location: String): Single<WeatherLocationData> = weatherService.getWeatherLocation(apiKey, location)


}
