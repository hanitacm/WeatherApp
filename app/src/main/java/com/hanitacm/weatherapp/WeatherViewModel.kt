package com.hanitacm.weatherapp

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
  private val subscription = CompositeDisposable()

  fun getWeatherData(location: String) {
    if (location.isNotBlank()) {

      subscription.add(getWeatherUseCase.getWeather(location)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe())

    }
  }

  override fun onCleared() {
    subscription.dispose()
    super.onCleared()
  }

  class WeatherViewModelFactory(private val getWeatherUseCase: GetWeatherUseCase) :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return WeatherViewModel(getWeatherUseCase) as T
    }
  }
}
