package com.hanitacm.weatherapp

data class WeatherState(
    val weatherDataResult: DisplayableWeather,
    val searchInProgress: Boolean = false,
    val errorFounded: Boolean = false
)