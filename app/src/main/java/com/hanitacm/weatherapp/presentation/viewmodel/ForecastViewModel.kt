package com.hanitacm.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.domain.usecase.GetWeatherInMyLocationUseCase
import com.hanitacm.weatherapp.domain.usecase.GetWeatherUseCase
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import com.hanitacm.weatherapp.presentation.model.mapper.WeatherSuggestionMapper

class ForecastViewModel : ViewModel() {

  class WeatherViewModelFactory :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return ForecastViewModel() as T
    }
  }
}
