package com.hanitacm.weatherapp

class MainActivityPresenter(private val getWeatherUseCase: GetWeatherUseCase) {

  fun getWeatherSync(location: String) {

    if (!location.isBlank())
      getWeatherUseCase.getWeather(location)
  }
}
