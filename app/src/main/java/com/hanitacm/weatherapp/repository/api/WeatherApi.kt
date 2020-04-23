package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import com.hanitacm.weatherapp.repository.data.WeatherLocationData
import io.reactivex.Single
import javax.inject.Inject

class WeatherApi @Inject constructor(private val retrofit: RetrofitBase) {
  private val apiKey = "f33ec8d7f07ee949745b53d8474dbeb2"


  fun getWeather(location: String): Single<WeatherData> {

    val weatherService = retrofit.configRetrofitCall()
        .create(WeatherService::class.java)

    return weatherService.getWeatherData(apiKey, location)
  }

  fun getWeatherLocations(location: String): Single<WeatherLocationData>{
    val weatherService = retrofit.configRetrofitCall()
        .create(WeatherService::class.java)

    return weatherService.getWeatherLocation(apiKey, location)
  }


}
