package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.repository.api.WeatherApi
import io.reactivex.Single

class WeatherRepository(private val weatherApi: WeatherApi) {
  fun getWeather(location: String): Single<WeatherDomainModel> {
    return weatherApi.getWeather(location).map {
      WeatherDomainModel(it.weather[0].main, it.weather[0].description, it.main.temp, it.main.humidity)
    }
  }

}
