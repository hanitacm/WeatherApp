package com.hanitacm.weatherapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherViewModel
import com.hanitacm.weatherapp.presentation.viewmodel.ForecastViewModel
import com.hanitacm.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

class ViewModelBuilder {
  @Module
  abstract class ViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentLocationWeatherViewModel::class)
    abstract fun bindCurrentLocationWeatherViewModel(currentLocationWeatherViewModel: CurrentLocationWeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    abstract fun bindForecastViewModel(forecastViewModel: ForecastViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
  }
}