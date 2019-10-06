package com.hanitacm.weatherapp.repository.data

import com.hanitacm.weatherapp.domain.Coordinates
import com.hanitacm.weatherapp.domain.Temperature
import com.hanitacm.weatherapp.domain.WeatherDomainModel

class WeatherDataDomainMapper {

  fun mapToDomainModel(weatherSet: List<WeatherData>?)
      : List<WeatherDomainModel> = weatherSet?.map { mapWeatherToDomainModel(it) } ?: emptyList()

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
          , data.main.pressure)


}
