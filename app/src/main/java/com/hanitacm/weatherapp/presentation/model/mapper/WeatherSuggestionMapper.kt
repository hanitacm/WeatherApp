package com.hanitacm.weatherapp.presentation.model.mapper

import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.presentation.model.WeatherSuggestion
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherSuggestionMapper @Inject constructor() {
  fun mapToView(weatherDomainModel: List<WeatherDomainModel>): List<WeatherSuggestion> =
      weatherDomainModel.map {
        WeatherSuggestion(it.icon, "${it.location}, ${it.country}", it.temperature.temperature.roundToInt().toString(), it.coordinates.latitude, it.coordinates.longitude)
      }

}
