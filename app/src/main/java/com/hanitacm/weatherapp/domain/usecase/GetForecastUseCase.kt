package com.hanitacm.weatherapp.domain.usecase

import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(weatherRepository: WeatherRepository) {

  fun getForecast(location: UserLocationDomainModel) {
    //weatherRepository.
  }
}