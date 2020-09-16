package com.hanitacm.weatherapp.repository.data.mapper

import com.hanitacm.weatherapp.domain.model.Coordinates
import com.hanitacm.weatherapp.domain.model.Temperature
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.repository.data.WeatherForecastData
import java.util.Date
import javax.inject.Inject

class ForecastDataDomainMapper @Inject constructor() {

  fun mapToDomainModel(weatherSet: WeatherForecastData) =
      weatherSet.daily.map {
        WeatherDomainModel(
            coordinates = Coordinates(weatherSet.lon, weatherSet.lat),
            location = weatherSet.timezone,
            country = "",
            weather = it.weather.first().main,
            temperature = Temperature(it.temp.day, it.temp.max, it.temp.min),
            humidity = it.humidity,
            wind = it.windSpeed,
            clouds = it.clouds,
            pressure = it.pressure,
            icon = "http://openweathermap.org/img/wn/${it.weather.first().icon}@2x.png",
            date = Date(it.dt * 1000))
      }
}




