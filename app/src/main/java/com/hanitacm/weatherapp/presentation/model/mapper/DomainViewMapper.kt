package com.hanitacm.weatherapp.presentation.model.mapper

import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather

class DomainViewMapper {
  fun mapToView(weatherDomainModel: List<WeatherDomainModel>): List<DisplayableWeather> =
      weatherDomainModel.map {
        DisplayableWeather("${it.location}, ${it.country}",
            it.weather,
            it.temperature.temperature.toString(),
            it.temperature.minTemperature.toString(),
            it.temperature.maxTemperature.toString(),
            it.humidity,
            it.icon)
      }


}
