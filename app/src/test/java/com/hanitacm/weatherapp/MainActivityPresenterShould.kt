package com.hanitacm.weatherapp

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.junit.MockitoJUnit

class MainActivityPresenterShould {
  @get:Rule
  val rule = MockitoJUnit.rule()


  @Mock
  private lateinit var getWeatherUseCase: GetWeatherUseCase

  private lateinit var presenter: MainActivityPresenter

  companion object {
    private const val STRING_EMPTY = " "
  }

  @Before
  fun setUp() {
    presenter = MainActivityPresenter(getWeatherUseCase)
  }


  @Test
  fun validate_that_a_location_is_defined() {

    presenter.getWeatherSync(STRING_EMPTY)

    verifyZeroInteractions(getWeatherUseCase)
  }

  @Test
  fun get_weather_data_from_use_case() {
    val location = "Sydney"

    presenter.getWeatherSync(location)

    verify(getWeatherUseCase).getWeather(location)
  }
}