package com.hanitacm.weatherapp.di

import com.hanitacm.weatherapp.WeatherMainFragmentShould
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Singleton
@Component(modules = [ViewModelBuilder::class,
  TestNetworkModule::class,
  TestLocationModule::class])
interface TestAppComponent : AppComponent {

  fun inject(weatherMainFragment: WeatherMainFragmentShould)
  fun okHttpClient(): OkHttpClient
}