package com.hanitacm.weatherapp.repository.datasource.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import com.hanitacm.weatherapp.repository.data.WeatherForecastData
import com.hanitacm.weatherapp.repository.data.WeatherLocationData
import com.hanitacm.weatherapp.repository.datasource.api.exception.NoNetworkConnectionThrowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApi @Inject constructor(private val weatherService: WeatherService, private val networkUtils: NetworkUtils) {
  private val apiKey = "f33ec8d7f07ee949745b53d8474dbeb2"

  fun getWeather(latitude: String, longitude: String): Single<WeatherData> {

    return if (networkUtils.isOnline()) {
      weatherService.getWeatherData(apiKey, latitude, longitude)
    } else {
      Single.error(NoNetworkConnectionThrowable())
    }
  }

  fun getWeatherLocations(location: String): Single<WeatherLocationData> {
    return if (networkUtils.isOnline()) {
      weatherService.getWeatherLocation(apiKey, location)
    } else {
      Single.error(NoNetworkConnectionThrowable())
    }
  }

  fun getWeatherForecast(latitude: String, longitude: String): Single<WeatherForecastData> {
    return if (networkUtils.isOnline()) {
      weatherService.getWeatherForecast(apiKey, latitude, longitude)
    } else {
      Single.error(NoNetworkConnectionThrowable())
    }
  }


}
