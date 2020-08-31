package com.hanitacm.weatherapp.presentation.viewmodel

import com.hanitacm.weatherapp.domain.model.ErrorModel
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.model.WeatherSuggestion

sealed class CurrentLocationWeatherState {
  object Loading : CurrentLocationWeatherState()
  data class LocationSuggestionsLoaded(val weatherSuggestions: List<WeatherSuggestion>) : CurrentLocationWeatherState()
  data class WeatherLoaded(val weather: List<DisplayableWeather>) : CurrentLocationWeatherState()
  data class WeatherLoadFailure(val error: ErrorModel) : CurrentLocationWeatherState()
}