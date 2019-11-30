package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.repository.api.WeatherApi
import com.hanitacm.weatherapp.repository.data.WeatherDataDomainMapper
import io.reactivex.Single

class WeatherRepository(private val weatherApi: WeatherApi, private val mapper: WeatherDataDomainMapper) {
  fun getWeather(location: String): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeather(location).map { mapper.mapToDomainModel(it) }
  }
}


