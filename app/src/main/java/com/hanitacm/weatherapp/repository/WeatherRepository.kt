package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.repository.api.WeatherApi
import com.hanitacm.weatherapp.repository.data.mapper.WeatherDataDomainMapper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi, private val mapper: WeatherDataDomainMapper) {
  fun getWeather(location: String): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeather(location).map { mapper.mapToDomainModel(it) }
  }

  fun getWeatherLocations(location: String) : Single<List<WeatherDomainModel>>{
    return weatherApi.getWeatherLocations(location).map{
      mapper.mapLocationsDataToDomainModel(it)
    }
  }
}


