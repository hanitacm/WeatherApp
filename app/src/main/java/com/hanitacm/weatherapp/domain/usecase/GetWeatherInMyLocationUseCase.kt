package com.hanitacm.weatherapp.domain.usecase

import com.hanitacm.weatherapp.domain.ErrorHandler
import com.hanitacm.weatherapp.domain.Result
import com.hanitacm.weatherapp.domain.extensions.toResult
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.repository.UserLocationRepository
import com.hanitacm.weatherapp.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherInMyLocationUseCase @Inject constructor(private val userLocationRepository: UserLocationRepository, private val weatherRepository: WeatherRepository, private val errorHandler: ErrorHandler) {

  fun getWeatherInMyLocation(): Single<Result<List<WeatherDomainModel>>> {

    return userLocationRepository.getUserLocation()
        .flatMap { location -> weatherRepository.getWeather(location) }
        .toResult(errorHandler)


  }
}