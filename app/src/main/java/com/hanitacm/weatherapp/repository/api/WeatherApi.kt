package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.repository.data.WeatherData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApi {
  private val weatherAPIUrl = "http://api.openweathermap.org/data/2.5/"
  private val apiKey = "f33ec8d7f07ee949745b53d8474dbeb2"

  fun getWeather(location: String): Single<WeatherData> {
    val retrofit = Retrofit.Builder()
        .baseUrl(weatherAPIUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(WeatherService::class.java)

    return service.getWeatherData(apiKey, location)
  }


}
