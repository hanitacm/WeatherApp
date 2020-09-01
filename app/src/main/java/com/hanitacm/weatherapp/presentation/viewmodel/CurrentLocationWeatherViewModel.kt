package com.hanitacm.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.domain.Result
import com.hanitacm.weatherapp.domain.model.ErrorModel
import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.domain.usecase.GetWeatherInMyLocationUseCase
import com.hanitacm.weatherapp.domain.usecase.GetWeatherUseCase
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import com.hanitacm.weatherapp.presentation.model.mapper.WeatherSuggestionMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrentLocationWeatherViewModel @Inject constructor(private val getWeatherInMyLocationUseCase: GetWeatherInMyLocationUseCase, private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper, private val mapperSuggestion: WeatherSuggestionMapper) : ViewModel() {

  private val _viewState = MutableLiveData<CurrentLocationWeatherState>()
  val viewState: LiveData<CurrentLocationWeatherState>
    get() = _viewState


  private val subscription = CompositeDisposable()

  fun getCurrentLocationWeather() {
    _viewState.value = CurrentLocationWeatherState.Loading

    subscription.add(getWeatherInMyLocationUseCase.getWeatherInMyLocation()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { result -> processResponse(result) }
    )
  }


  fun loadLocationSuggestions(location: String) {
    if (location.isNotBlank() && location.length > 2) {

      subscription.add(getWeatherUseCase.getWeather(location)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe { result -> processSuggestionsResponse(result) }
      )

    }
  }

  fun getWeather(latitude: Double, longitude: Double) {


    subscription.add(getWeatherUseCase.getWeather(UserLocationDomainModel(latitude, longitude))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { result -> processResponse(result) }
    )


  }

  private fun processResponse(result: Result<List<WeatherDomainModel>>) {
    when (result) {
      is Result.Success ->
        _viewState.postValue(CurrentLocationWeatherState.WeatherLoaded(mapper.mapToView(result.data)))
      else -> showError((result as Result.Error).error)

    }

  }

  private fun processSuggestionsResponse(result: Result<List<WeatherDomainModel>>) {
    when (result) {
      is Result.Success -> _viewState.postValue(CurrentLocationWeatherState.LocationSuggestionsLoaded(mapperSuggestion.mapToView(result.data)))
      else -> showError((result as Result.Error).error)

    }
  }

  private fun showError(error: ErrorModel) {
    _viewState.postValue(CurrentLocationWeatherState.WeatherLoadFailure(error))

  }

  override fun onCleared() {
    subscription.dispose()
    super.onCleared()
  }

  class WeatherViewModelFactory(private val getWeatherInMyLocationUseCase: GetWeatherInMyLocationUseCase, private val getWeatherUseCase: GetWeatherUseCase, private val mapper: DomainViewMapper, private val mapperSuggestion: WeatherSuggestionMapper) :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return CurrentLocationWeatherViewModel(getWeatherInMyLocationUseCase, getWeatherUseCase, mapper, mapperSuggestion) as T
    }
  }

}