package com.hanitacm.weatherapp.repository.data.mapper

import com.hanitacm.weatherapp.domain.model.Coordinates
import com.hanitacm.weatherapp.domain.model.Temperature
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.repository.data.WeatherForecastData
import javax.inject.Inject

class ForecastDataDomainMapper @Inject constructor() {

  fun mapToDomainModel(weatherSet: WeatherForecastData) =
      weatherSet.list.map {
        WeatherDomainModel(
            coordinates = Coordinates(weatherSet.city.coord.lon, weatherSet.city.coord.lat),
            location = weatherSet.city.name,
            country = weatherSet.city.country,
            weather = it.weather.first().main,
            temperature = Temperature(it.main.temp, it.main.tempMax, it.main.tempMin),
            humidity = it.main.humidity,
            wind = it.wind.speed,
            clouds = it.clouds.all,
            pressure = it.main.pressure,
            icon = "http://openweathermap.org/img/wn/${it.weather.first().icon}@2x.png")
      }
}




