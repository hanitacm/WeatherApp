package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.repository.WeatherRepository
import io.reactivex.Single

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
  fun getWeather(location: String): Single<List<WeatherDomainModel>> {
    return weatherRepository.getWeather(location)
  }

}
