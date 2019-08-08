package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.repository.WeatherRepository
import com.hanitacm.weatherapp.repository.api.WeatherApi
import io.reactivex.Single

class GetWeatherUseCase(val weatherRepository: WeatherRepository) {
  fun getWeather(location: String): Single<String> {
    return weatherRepository.getWeather(location)
  }

}
