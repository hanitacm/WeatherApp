package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.repository.data.mapper.ForecastDataDomainMapper
import com.hanitacm.weatherapp.repository.data.mapper.WeatherDataDomainMapper
import com.hanitacm.weatherapp.repository.datasource.api.WeatherApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi, private val mapper: WeatherDataDomainMapper, private val forecastMapper: ForecastDataDomainMapper) {
  fun getWeather(location: UserLocationDomainModel): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeather(location.latitude.toString(), location.longitude.toString()).map { mapper.mapToDomainModel(it) }
  }

  fun getWeatherLocations(location: String): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeatherLocations(location).map {
      mapper.mapLocationsDataToDomainModel(it)
    }
  }

  fun getWeatherForecast(location: UserLocationDomainModel): Single<List<WeatherDomainModel>> {
    return weatherApi.getWeatherForecast(location.latitude.toString(), location.longitude.toString()).map {
      forecastMapper.mapToDomainModel(it)
    }
  }
}


