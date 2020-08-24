package com.hanitacm.weatherapp.di

import androidx.test.espresso.IdlingResource
import com.hanitacm.weatherapp.BuildConfig
import com.hanitacm.weatherapp.repository.api.WeatherService
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class TestNetworkModule {
  private val weatherAPIUrl = "http://localhost:8080/"

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
  fun provideRetrofit(client: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .baseUrl(weatherAPIUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
  }

  @Singleton
  @Provides
  fun provideWeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

  @Singleton
  @Provides
  fun providesIdlingResource(okHttpClient: OkHttpClient): IdlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)


}
