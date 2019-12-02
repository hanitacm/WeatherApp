package com.hanitacm.weatherapp.presentation.model

data class DisplayableWeather(val location: String,
                              val description: String,
                              val temperature: String,
                              val temperature_min: String,
                              val temperature_max: String,
                              val humidity: Int,
                              val icon: String)


