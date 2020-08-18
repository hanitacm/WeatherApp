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
import com.hanitacm.weatherapp.presentation.model.WeatherSuggestion
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import com.hanitacm.weatherapp.presentation.model.mapper.WeatherSuggestionMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrentLocationWeatherViewModel @Inject constructor(private val getUserLocationUseCase: GetUserLocationUseCase, private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper, private val mapperSuggestion: WeatherSuggestionMapper) : ViewModel() {
  private val weather = MutableLiveData<List<DisplayableWeather>>()
  val getWeather: LiveData<List<DisplayableWeather>>
    get() = weather

  private val weatherSuggestions = MutableLiveData<List<WeatherSuggestion>>()
  val getWeatherSuggestions: LiveData<List<WeatherSuggestion>>
    get() = weatherSuggestions


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

  fun loadLocationSuggestions(location: String) {
    if (location.isNotBlank()) {

      subscription.add(getWeatherUseCase.getWeather(location)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({ result -> processSuggestionsResponse(result) }
              , { error -> showError(error) })
      )

    }
  }

  fun getWeather(latitude: Double, longitude: Double) {


    subscription.add(getWeatherUseCase.getWeather(UserLocationDomainModel(latitude, longitude))
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
    weather.postValue(mapper.mapToView(result))

  }

  private fun processSuggestionsResponse(result: List<WeatherDomainModel>) {
    weatherSuggestions.postValue(mapperSuggestion.mapToView(result))

  }

  override fun onCleared() {
    subscription.dispose()
    super.onCleared()
  }

  class WeatherViewModelFactory(private val getUserLocationUseCase: GetUserLocationUseCase, private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper, private val mapperSuggestion: WeatherSuggestionMapper) :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return CurrentLocationWeatherViewModel(getUserLocationUseCase, getWeatherUseCase, mapper, mapperSuggestion) as T
    }
  }

}