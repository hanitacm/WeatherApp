package com.hanitacm.weatherapp.presentation.viewmodel

import com.hanitacm.weatherapp.domain.model.ErrorModel
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.model.WeatherSuggestion

sealed class ForecastViewState {
  object Loading : ForecastViewState()
  data class ForecastLoaded(val weather: List<DisplayableWeather>) : ForecastViewState()
  data class ForecastLoadFailure(val error: ErrorModel) : ForecastViewState()

}