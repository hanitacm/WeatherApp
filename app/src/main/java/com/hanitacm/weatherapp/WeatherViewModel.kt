package com.hanitacm.weatherapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import com.hanitacm.weatherapp.domain.WeatherDomainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
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
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun processResponse(result: List<WeatherDomainModel>) {
    Log.d("error", result.toString())
    val displayableItems: List<DisplayableWeather> = result.map { DisplayableWeather("", 10.0, it.humidity, "CO") }
    weather.postValue(displayableItems)

    //weather.postValue(DisplayableWeather(result.description, result.temperature, result.humidity, result.))
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
