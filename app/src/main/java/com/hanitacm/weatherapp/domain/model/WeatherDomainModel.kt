package com.hanitacm.weatherapp.domain.model

import java.util.Date

data class WeatherDomainModel(val coordinates: Coordinates,
                              val location: String,
                              val country: String,
                              val weather: String,
                              val temperature: Temperature,
                              val humidity: Int,
                              val wind: Double,
                              val clouds: Int,
                              val pressure: Int,
                              val icon: String,
                              val date: Date,
                              val description: String)

data class Coordinates(val longitude: Double, val latitude: Double)
data class Temperature(val temperature: Double, val maxTemperature: Double, val minTemperature: Double)
//coords
//name
//country (sys/country)
//weather (weather[0]/main)
//temp (main/temp)
// max temp (main/temp_max)
//min temp (main/temp_min)
//wind speed (wind/speed)
//clouds (clouds/all)
//humidity (main/humidity)
//pressure (main/pressure)
//9.2°С temperature from 9.2 to 9.2 °С, wind 3.31 m/s. clouds 0 %, 1018.21 hpa