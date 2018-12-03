package com.hanitacm.weatherapp

import com.hanitacm.domainweather.GetWeatherUseCase

class MainActivityPresenter(private val getWeatherUseCase: GetWeatherUseCase) {

  fun getWeatherSync(location: String) {

    if (!location.isBlank())
      getWeatherUseCase.getWeather(location)
  }
}
