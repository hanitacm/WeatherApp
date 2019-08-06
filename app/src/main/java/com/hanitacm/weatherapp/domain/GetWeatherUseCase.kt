package com.hanitacm.weatherapp.domain

import io.reactivex.Single

class GetWeatherUseCase {
  fun getWeather(location: String): Single<String> {
    return Single.just<String>("")
  }

}
