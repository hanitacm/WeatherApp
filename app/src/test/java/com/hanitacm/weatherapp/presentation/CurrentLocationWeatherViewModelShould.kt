package com.hanitacm.weatherapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hanitacm.weatherapp.RxSchedulerRule
import com.hanitacm.weatherapp.domain.Coordinates
import com.hanitacm.weatherapp.domain.GetUserLocationUseCase
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import com.hanitacm.weatherapp.domain.Temperature
import com.hanitacm.weatherapp.domain.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.WeatherDomainModel
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.model.WeatherSuggestion
import com.hanitacm.weatherapp.presentation.model.mapper.DomainViewMapper
import com.hanitacm.weatherapp.presentation.model.mapper.WeatherSuggestionMapper
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherState
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class CurrentLocationWeatherViewModelShould {
  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val rule = RxSchedulerRule()

  @Mock
  private lateinit var getUserLocationUseCase: GetUserLocationUseCase

  @Mock
  private lateinit var getWeatherUseCase: GetWeatherUseCase

  @Spy
  private lateinit var mapper: DomainViewMapper

  @Spy
  private lateinit var mapperSuggestion: WeatherSuggestionMapper

  @Mock
  lateinit var observer: Observer<CurrentLocationWeatherState>


  @InjectMocks
  private lateinit var currentLocationWeatherViewModel: CurrentLocationWeatherViewModel

  @Before
  fun setUp() {

    currentLocationWeatherViewModel.viewState.observeForever(observer)
  }

  @Test
  fun `get current location weather`() {
    val weatherDomainModel = validWeather()

    whenever(getUserLocationUseCase.getUserLocation()).thenReturn(Single.just(validLocation()))
    whenever(getWeatherUseCase.getWeather(validLocation())).thenReturn(Single.just(weatherDomainModel))

    currentLocationWeatherViewModel.getCurrentLocationWeather()

    verify(mapper).mapToView(weatherDomainModel)
    verify(observer).onChanged(CurrentLocationWeatherState.Loading)
    verify(observer).onChanged(CurrentLocationWeatherState.WeatherLoaded(validDisplayableWeather()))

  }

  @Test
  fun `load location suggestions`() {
    val location = "Barcelona"
    val weatherDomainModel = validWeather()


    whenever(getWeatherUseCase.getWeather(location)).thenReturn(Single.just(weatherDomainModel))


    currentLocationWeatherViewModel.loadLocationSuggestions(location)

    verify(mapperSuggestion).mapToView(weatherDomainModel)
    verify(observer).onChanged(CurrentLocationWeatherState.LocationSuggestionsLoaded(validSuggestions()))
  }

  @Test
  fun `not look for suggestions if string has less than 3 characters`() {

    currentLocationWeatherViewModel.loadLocationSuggestions("Ba")

    verify(getWeatherUseCase, never()).getWeather("Ba")
    verify(observer, never()).onChanged(any())

  }

  //  @Test
//  fun `show error message if it cannot get weather`() {
//    val errorMessage = "error"
//    whenever(getUserLocationUseCase.getUserLocation()).thenThrow(Throwable(errorMessage))
//
//    verify(observer).onChanged(CurrentLocationWeatherState.WeatherLoadFailure(errorMessage))
//    verify(observer, never()).onChanged(CurrentLocationWeatherState.WeatherLoaded(validDisplayableWeather()))
//
//  }
  private fun validSuggestions(): List<WeatherSuggestion> {
    return listOf(WeatherSuggestion(icon = "04n",
        location = "Bunbury, AU",
        temperature = "11",
        latitude = 4.0,
        longitude = 3.0))
  }

  private fun validDisplayableWeather(): List<DisplayableWeather> {
    return listOf(DisplayableWeather("Bunbury, AU", "Clouds", "11", "11.11", "11.11", "54%", "04n", "1012hPa", "4.46m/s")
    )
  }

  private fun validWeather(): List<WeatherDomainModel> {
    return listOf(WeatherDomainModel(Coordinates(3.0, 4.0), "Bunbury", "AU", "Clouds", Temperature(11.11, 11.11, 11.11), 54, 4.46, 77, 1012, "04n")
    )
  }

  private fun validLocation() = UserLocationDomainModel(4.0, 3.0)
}