package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.repository.data.Clouds
import com.hanitacm.weatherapp.repository.data.Coord
import com.hanitacm.weatherapp.repository.data.Daily
import com.hanitacm.weatherapp.repository.data.FeelsLike
import com.hanitacm.weatherapp.repository.data.Main
import com.hanitacm.weatherapp.repository.data.Sys
import com.hanitacm.weatherapp.repository.data.Temp
import com.hanitacm.weatherapp.repository.data.Weather
import com.hanitacm.weatherapp.repository.data.WeatherData
import com.hanitacm.weatherapp.repository.data.WeatherForecastData
import com.hanitacm.weatherapp.repository.data.Wind
import com.hanitacm.weatherapp.repository.data.mapper.ForecastDataDomainMapper
import com.hanitacm.weatherapp.repository.data.mapper.WeatherDataDomainMapper
import com.hanitacm.weatherapp.repository.datasource.api.WeatherApi
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryShould {
  @Mock
  private lateinit var weatherApi: WeatherApi

  @Spy
  private lateinit var mapper: WeatherDataDomainMapper

  @Spy
  private lateinit var forecastMapper: ForecastDataDomainMapper

  @InjectMocks
  private lateinit var weatherRepository: WeatherRepository

  @Test
  fun `get weather data from api and map to domain model`() {
    val location = givenAUserLocation()
    val weatherApiResponse = givenAFakeWeatherData()
    whenever(weatherApi.getWeather(location.latitude.toString(), location.longitude.toString())).thenReturn(Single.just(weatherApiResponse))

    val response = weatherRepository.getWeather(location).test()

    verify(weatherApi).getWeather(location.latitude.toString(), location.longitude.toString())
    verify(mapper).mapToDomainModel(weatherApiResponse)

    Assert.assertNotNull(response)
  }

  @Test
  fun `get forecast weather from api and map to domain model`() {
    val location = givenAUserLocation()
    val weatherApiResponse = givenAFakeForecastData()
    whenever(weatherApi.getWeatherForecast(location.latitude.toString(), location.longitude.toString())).thenReturn(Single.just(weatherApiResponse))

    val response = weatherRepository.getWeatherForecast(location).test()

    verify(weatherApi).getWeatherForecast(location.latitude.toString(), location.longitude.toString())
    verify(forecastMapper).mapToDomainModel(weatherApiResponse)

    Assert.assertNotNull(response)

  }

  private fun givenAFakeForecastData() =
      WeatherForecastData(
          listOf(Daily(99,
              295.75,
              1599242400,
              FeelsLike(205.65, 302.81, 297.31, 300.03),
              69,
              22,
              1013,
              0.0,
              15,
              1599393247,
              Temp(20.5, 21.3, 22.0, 19.4, 20.1, 19.4),
              80.6,
              listOf(Weather("clear sky", "04d", 804, "clouds")),
              45,
              34.7),
              Daily(99,
                  295.75,
                  1599242400,
                  FeelsLike(205.65, 302.81, 297.31, 300.03),
                  69,
                  72,
                  1013,
                  11.7,
                  15,
                  1599393247,
                  Temp(20.5, 21.3, 22.0, 19.4, 20.1, 19.4),
                  80.6,
                  listOf(Weather("clear sky", "04d", 804, "clouds")),
                  45,
                  34.7)),
          4.0,
          3.0,
          "Montana",
          18000)


  private fun givenAFakeWeatherData() =
      WeatherData("stations",
          Clouds(3),
          cod = 22,
          coord = Coord(4.0, 3.0),
          dt = 1597748537,
          id = 200878,
          main = Main(humidity = 5, pressure = 1012, temp = 30.0, tempMax = 31.1, tempMin = 29.5),
          name = "Sevilla",
          sys = Sys(country = "ES", id = 2000, message = 12.2, sunrise = 1587727839, sunset = 1597776720, type = 3),
          timezone = 7200,
          visibility = 10000,
          weather = listOf(Weather("clear sky", "01d", id = 800, main = "Clear")),
          wind = Wind(45, 0.89))


  private fun givenAUserLocation() = UserLocationDomainModel(4.0, 3.0)
}