package com.hanitacm.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.util.Log
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper) : ViewModel() {
  private val subscription = CompositeDisposable()

  private val weather = MutableLiveData<List<DisplayableWeather>>()
  val getWeather: LiveData<List<DisplayableWeather>>
    get() = weather


  fun loadWeather(location: String) {
    if (location.isNotBlank()) {

      subscription.add(getWeatherUseCase.getWeather(location)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ result -> processResponse(result) }
              , { error -> showError(error) })
      )

    }
  }

  private fun showError(error: Throwable?) {
    Log.d("error", error.toString())//To change body of created functions use File | Settings | File Templates.
  }

  private fun processResponse(result: List<WeatherDomainModel>) {
    Log.d("info", result.toString())
    weather.postValue(mapper.mapToView(result))

    //weather.postValue(DisplayableWeather(result.description, result.temperature, result.humidity, result.))
  }

  override fun onCleared() {
    subscription.dispose()
    super.onCleared()
  }

  class WeatherViewModelFactory(private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper) :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return WeatherViewModel(getWeatherUseCase, mapper) as T
    }
  }
}
