package com.hanitacm.weatherapp.domain.usecase

import com.hanitacm.weatherapp.domain.ErrorHandler
import com.hanitacm.weatherapp.domain.Result
import com.hanitacm.weatherapp.domain.extensions.toResult
import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository, private val errorHandler: ErrorHandler) {
  fun getWeather(location: String): Single<Result<List<WeatherDomainModel>>> {
    return weatherRepository.getWeatherLocations(location).toResult(errorHandler)
  }

  fun getWeather(location: UserLocationDomainModel): Single<Result<List<WeatherDomainModel>>> {
    return weatherRepository.getWeather(location).toResult(errorHandler)
  }

}
