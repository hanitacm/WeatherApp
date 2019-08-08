package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.repository.WeatherRepository
import io.reactivex.Single

class GetWeatherUseCase(weatherRepository: WeatherRepository) {
  fun getWeather(location: String): Single<String> {
    return Single.just<String>("")
  }

}
