package com.hanitacm.weatherapp.repository

import android.location.Location
import com.hanitacm.weatherapp.repository.data.mapper.UserLocationDomainMapper
import com.hanitacm.weatherapp.repository.datasource.provider.LocationProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserLocationRepositoryShould {
  @Mock
  private lateinit var locationProvider: LocationProvider

  @Spy
  private lateinit var mapper: UserLocationDomainMapper

  @InjectMocks
  private lateinit var userLocationRepository: UserLocationRepository


  @Test
  fun `return last valid location`() {


    val location = givenALocationFromGPS()



    whenever(locationProvider.requestLocation()).thenReturn(Single.just(location))
    whenever(mapper.mapToDomainModel(location)).thenCallRealMethod()


    val locationResult = userLocationRepository.getUserLocation().test()


    locationResult.awaitTerminalEvent()
    locationResult.assertNoErrors()


    Mockito.verify(locationProvider).requestLocation()
    Mockito.verify(mapper).mapToDomainModel(location)

    locationResult.assertValue { it.longitude == location.longitude && it.latitude == location.latitude }

  }

  @Test
  fun `throw an exception when there are some errors`() {
    whenever(locationProvider.requestLocation()).thenReturn(Single.error(RuntimeException()))
    //.error(IllegalStateException("Location is null")))

    val locationResult = userLocationRepository.getUserLocation().test()

    locationResult.awaitTerminalEvent()
    locationResult.assertError(RuntimeException::class.java)


    verify(locationProvider).requestLocation()
    verify(mapper, never()).mapToDomainModel(any())


  }

  private fun givenALocationFromGPS(): Location = Location("test_provider").apply {
    latitude = 40.0
    longitude = 3.0
    time = System.currentTimeMillis()
  }
}