package com.hanitacm.weatherapp.repository.data.mapper

import com.hanitacm.weatherapp.domain.Coordinates
import com.hanitacm.weatherapp.domain.Temperature
import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.repository.data.WeatherData
import com.hanitacm.weatherapp.repository.data.WeatherLocationData

class WeatherDataDomainMapper {

  fun mapToDomainModel(weatherSet: WeatherData)
      : List<WeatherDomainModel> = listOf(mapWeatherToDomainModel(weatherSet))

  private fun mapWeatherToDomainModel(data: WeatherData): WeatherDomainModel =
      WeatherDomainModel(
          Coordinates(data.coord.lon, data.coord.lat)
          , data.name
          , data.sys.country
          , data.weather[0].main
          , Temperature(data.main.temp
          , data.main.tempMax
          , data.main.tempMin)
          , data.main.humidity
          , data.wind.speed
          , data.clouds.all
          , data.main.pressure
          ,data.weather[0].icon)

  fun mapLocationsDataToDomainModel(data: WeatherLocationData): List<WeatherDomainModel> {
    return if (data.count > 0) data.list.map { mapWeatherToDomainModel(it) }
           else emptyList()
  }
}


