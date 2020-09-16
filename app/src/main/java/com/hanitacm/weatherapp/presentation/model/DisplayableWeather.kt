package com.hanitacm.weatherapp.presentation.model

data class DisplayableWeather(val location: String,
                              val description: String,
                              val temperature: String,
                              val temperature_min: String,
                              val temperature_max: String,
                              val humidity: String,
                              val icon: String,
                              val pressure: String,
                              val wind: String,
                              val latitude: Double,
                              val longitude: Double,
                              val date: String)


