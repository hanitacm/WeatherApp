package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.repository.data.mapper.WeatherDataDomainMapper
import com.hanitacm.weatherapp.repository.datasource.api.WeatherApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi, private val mapper: WeatherDataDomainMapper) {
  fun getWeather(location: UserLocationDomainModel): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeather(location.latitude.toString(), location.longitude.toString()).map { mapper.mapToDomainModel(it) }
  }

  fun getWeatherLocations(location: String): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeatherLocations(location).map {
      mapper.mapLocationsDataToDomainModel(it)
    }
  }

  fun getWeatherForecast(location: UserLocationDomainModel) {
    return weatherApi.getWeatherForecast(location.latitude, location.longitude)
  }
}


