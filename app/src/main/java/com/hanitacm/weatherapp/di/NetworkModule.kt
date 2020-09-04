package com.hanitacm.weatherapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.hanitacm.weatherapp.BuildConfig
import com.hanitacm.weatherapp.repository.datasource.api.WeatherService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
  private val weatherAPIUrl = "http://api.openweathermap.org/data/2.5/"

  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    return interceptor
  }

  @Provides
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
      OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

  @Singleton
  @Provides
  fun provideRetrofit(client: OkHttpClient): Retrofit =
      Retrofit.Builder()
          .baseUrl(weatherAPIUrl)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .build()


  @Singleton
  @Provides
  fun provideWeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

  @Singleton
  @Provides
  fun provideConnectivityService(context: Context): ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}