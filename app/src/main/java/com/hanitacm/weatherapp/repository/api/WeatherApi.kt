package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import io.reactivex.Single

class WeatherApi(private val retrofit: RetrofitBase) {
  private val apiKey = "f33ec8d7f07ee949745b53d8474dbeb2"


  fun getWeather(location: String): Single<WeatherData> {

    val weatherService = retrofit.configRetrofitCall()
        .create(WeatherService::class.java)

    return weatherService.getWeatherData(apiKey, location)
  }


}
