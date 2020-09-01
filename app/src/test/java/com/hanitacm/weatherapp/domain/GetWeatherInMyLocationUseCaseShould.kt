package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.domain.usecase.GetWeatherInMyLocationUseCase
import com.hanitacm.weatherapp.repository.ErrorHandlerImpl
import com.hanitacm.weatherapp.repository.UserLocationRepository
import com.hanitacm.weatherapp.repository.WeatherRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetWeatherInMyLocationUseCaseShould {

  @Mock
  private lateinit var userLocationRepository: UserLocationRepository

  @Mock
  private lateinit var weatherRepository: WeatherRepository

  @Mock
  private lateinit var errorHandler: ErrorHandlerImpl

  @InjectMocks
  private lateinit var getMyWeatherInMyLocationUseCase: GetWeatherInMyLocationUseCase

  @Test
  fun `call user location repository`() {
    val validLocation = UserLocationDomainModel(4.0, 3.0)

    whenever(userLocationRepository.getUserLocation()).thenReturn(Single.just(validLocation))

    getMyWeatherInMyLocationUseCase.getWeatherInMyLocation().test()

    verify(userLocationRepository).getUserLocation()
    verify(weatherRepository).getWeather(validLocation)

  }
}