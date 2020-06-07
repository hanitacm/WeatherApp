package com.hanitacm.weatherapp.repository

import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.hanitacm.weatherapp.repository.data.mapper.UserLocationDomainMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.invocation.InvocationOnMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class UserLocationRepositoryShould {
  @Mock
  private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

  @Mock
  private lateinit var mockTask: Task<Location>

  @Spy
  private lateinit var mapper: UserLocationDomainMapper

  @InjectMocks
  private lateinit var userLocationRepository: UserLocationRepository

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

  }

  @Test
  fun `returns last valid location`() {


    val location = `given a location from gps`()



    whenever(fusedLocationProviderClient.lastLocation).thenReturn(mockTask)
    `when location provider returns a response`(location)
    whenever(mapper.mapToDomainModel(location)).thenCallRealMethod()


    val locationResult = userLocationRepository.getUserLocation().test()


    locationResult.awaitTerminalEvent()
    locationResult.assertNoErrors()


    verify(fusedLocationProviderClient).lastLocation
    verify(mapper).mapToDomainModel(location)

    locationResult.assertValue { it.longitude == location.longitude && it.latitude == location.latitude }

  }

  @Test
  fun `throws an exception when provider returns a null value as location`() {
    whenever(fusedLocationProviderClient.lastLocation).thenReturn(mockTask)
    `when location provider returns a response`(null)

    val locationResult = userLocationRepository.getUserLocation().test()

    locationResult.awaitTerminalEvent()
    locationResult.assertError(IllegalStateException::class.java)


    verify(fusedLocationProviderClient).lastLocation
    verify(mapper, times(0))


  }

  private fun `when location provider returns a response`(location: Location?) {
    doAnswer { invocation: InvocationOnMock? ->
      val arguments = invocation?.arguments
      val listener = arguments?.get(0) as OnSuccessListener<Location>
      listener.onSuccess(location)
      null
    }.`when`(mockTask).addOnSuccessListener(any())
  }

  private fun `given a location from gps`(): Location = Location("test_provider").apply {
    latitude = 40.0
    longitude = 3.0
    time = System.currentTimeMillis()
  }

  @AfterEach
  fun tearDown() {
  }
}