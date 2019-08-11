package com.hanitacm.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
  private val subscription = CompositeDisposable()

  private val weather = MutableLiveData<DisplayableWeather>()
  val getWeather: LiveData<DisplayableWeather>
    get() = weather


  fun loadWeather(location: String) {
    if (location.isNotBlank()) {

      subscription.add(getWeatherUseCase.getWeather(location)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ result -> weather.postValue(DisplayableWeather(result.description, result.temperature, result.humidity)) }
              , { error -> })
      )

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
