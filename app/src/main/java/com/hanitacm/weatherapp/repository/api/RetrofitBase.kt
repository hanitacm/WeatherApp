package com.hanitacm.weatherapp.repository.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBase {
  private val weatherAPIUrl = "http://api.openweathermap.org/data/2.5/"

  fun configRetrofitCall():Retrofit = Retrofit.Builder()
        .baseUrl(weatherAPIUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}