package com.hanitacm.weatherapp.repository.provider

import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class LocationGoogleServiceProviderShould {
  @Mock
  private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

  @Mock
  private lateinit var mockTask: Task<Location>

  @InjectMocks
  private lateinit var locationGoogleServiceProvider: LocationGoogleServiceProvider

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

  }

  @Test
  fun `return last valid location`() {


    val location = `given a location from gps`()



    whenever(fusedLocationProviderClient.lastLocation).thenReturn(mockTask)
    `when location provider returns a response`(location)


    val locationResult = locationGoogleServiceProvider.requestLocation().test()


    locationResult.awaitTerminalEvent()
    locationResult.assertNoErrors()


    verify(fusedLocationProviderClient).lastLocation

    locationResult.assertValue { it.longitude == location.longitude && it.latitude == location.latitude }

  }

  @Test
  fun `throw an exception when provider returns a null value as location`() {
    whenever(fusedLocationProviderClient.lastLocation).thenReturn(mockTask)
    `when location provider returns a response`(null)

    val locationResult = locationGoogleServiceProvider.requestLocation().test()

    locationResult.awaitTerminalEvent()
    locationResult.assertError(IllegalStateException::class.java)


    verify(fusedLocationProviderClient).lastLocation


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


}