package com.hanitacm.weatherapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.domain.GetUserLocationUseCase
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import com.hanitacm.weatherapp.domain.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrentLocationWeatherViewModel @Inject constructor(private val getUserLocationUseCase: GetUserLocationUseCase, private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper) : ViewModel() {
  private val weather = MutableLiveData<List<DisplayableWeather>>()
  val getWeather: LiveData<List<DisplayableWeather>>
    get() = weather

  private val subscription = CompositeDisposable()

  fun getCurrentLocationWeather() {
    subscription.add(
        getUserLocationUseCase.getUserLocation()
            .flatMap { location: UserLocationDomainModel -> getWeatherUseCase.getWeather(location).subscribeOn(Schedulers.io()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result -> processResponse(result) }
                , { error -> showError(error) })
    )
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

  class WeatherViewModelFactory(private val getUserLocationUseCase: GetUserLocationUseCase, private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper) :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return CurrentLocationWeatherViewModel(getUserLocationUseCase, getWeatherUseCase, mapper) as T
    }
  }

}