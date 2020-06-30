package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
  fun getWeather(location: String): Single<List<WeatherDomainModel>> {
    //return weatherRepository.getWeather(location)
    return weatherRepository.getWeatherLocations(location)
  }

  fun getWeather(location: UserLocationDomainModel): Single<List<WeatherDomainModel>> {
    return weatherRepository.getWeather(location)
  }

}
