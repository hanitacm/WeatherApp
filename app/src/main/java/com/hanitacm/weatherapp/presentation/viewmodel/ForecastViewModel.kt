package com.hanitacm.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.domain.Result
import com.hanitacm.weatherapp.domain.model.ErrorModel
import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.model.WeatherDomainModel
import com.hanitacm.weatherapp.domain.usecase.GetForecastUseCase
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val getForecastUseCase: GetForecastUseCase, private val mapper: DomainViewMapper) : ViewModel() {

  private val _viewState = MutableLiveData<ForecastViewState>()
  val viewState: LiveData<ForecastViewState>
    get() = _viewState

  private val subscription = CompositeDisposable()

  fun getForecastWeather(latitude: Double, longitude: Double) {

    subscription.add(getForecastUseCase.getForecast(UserLocationDomainModel(latitude, longitude))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { result -> processResponse(result) }
    )

  }

  private fun processResponse(result: Result<List<WeatherDomainModel>>) {
    when (result) {
      is Result.Success ->
        _viewState.postValue(ForecastViewState.ForecastLoaded(mapper.mapToView(result.data)))
      else -> showError((result as Result.Error).error)

    }
  }

  private fun showError(error: ErrorModel) {
    _viewState.postValue(ForecastViewState.ForecastLoadFailure(error))

  }

  class WeatherViewModelFactory(private val getForecastUseCase: GetForecastUseCase, private val mapper: DomainViewMapper) :
      ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      @Suppress("UNCHECKED_CAST")
      return ForecastViewModel(getForecastUseCase, mapper) as T
    }
  }

  override fun onCleared() {
    subscription.dispose()
    super.onCleared()
  }
}
