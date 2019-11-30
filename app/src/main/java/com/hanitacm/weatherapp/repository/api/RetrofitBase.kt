package com.hanitacm.weatherapp.repository.api

import com.hanitacm.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBase {
  private val weatherAPIUrl = "http://api.openweathermap.org/data/2.5/"

  fun configRetrofitCall(): Retrofit {

    val interceptor = HttpLoggingInterceptor()

    interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    return Retrofit.Builder()
        .baseUrl(weatherAPIUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

  }
}