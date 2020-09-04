//package com.hanitacm.weatherapp.repository
//
///**
// * Copyright 2018-2019 rideOS, Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//import ai.rideos.android.common.model.LatLng
//import ai.rideos.android.common.model.LocationAndHeading
//import ai.rideos.android.common.reactive.SchedulerProviders.TrampolineSchedulerProvider
//import android.location.Location
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.tasks.OnSuccessListener
//import com.google.android.gms.tasks.Task
//import io.reactivex.Observable
//import io.reactivex.observers.TestObserver
//import io.reactivex.schedulers.Schedulers
//import org.junit.Before
//import org.junit.Test
//import org.mockito.ArgumentCaptor
//import org.mockito.ArgumentMatchers
//import org.mockito.Mockito
//import java.io.IOException
//import java.util.Arrays
//import java.util.function.Consumer
//
//
//class FusedLocationDeviceLocatorTest {
//  private var client: FusedLocationProviderClient? = null
//  private var locatorUnderTest: FusedLocationDeviceLocator? = null
//  private var permissionsChecker: PermissionsChecker? = null
//
//  @Before
//  fun setUp() {
//    client = Mockito.mock(FusedLocationProviderClient::class.java)
//    permissionsChecker = Mockito.mock(PermissionsChecker::class.java)
//    locatorUnderTest = FusedLocationDeviceLocator(
//        { client },
//        permissionsChecker,
//        TrampolineSchedulerProvider()
//    )
//    Mockito.`when`(permissionsChecker.areLocationPermissionsGranted()).thenReturn(true)
//  }
//
//  @Test
//  fun testObserveErrorWhenPermissionsNotGranted() {
//    Mockito.`when`(permissionsChecker.areLocationPermissionsGranted()).thenReturn(false)
//    locatorUnderTest.observeCurrentLocation(POLL_INTERVAL_MILLIS).test()
//        .assertValueCount(0)
//        .assertError({ error -> error is PermissionsNotGrantedException })
//  }
//
//  @Test
//  fun testLocatorRequestsLocationUpdatesWhenObserverSubscribes() {
//    val observableLocation: Observable<LocationAndHeading> = locatorUnderTest.observeCurrentLocation(POLL_INTERVAL_MILLIS)
//    Mockito.verifyNoMoreInteractions(client)
//    observableLocation.subscribeOn(Schedulers.trampoline()).subscribe()
//    Mockito.verify(client).requestLocationUpdates(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
//  }
//
//  @Test
//  fun testCanReceiveLocationUpdatesAfterSubscription() {
//    val callbackCaptor = ArgumentCaptor.forClass(LocationCallback::class.java)
//    val testObserver: TestObserver<LocationAndHeading> = locatorUnderTest.observeCurrentLocation(POLL_INTERVAL_MILLIS)
//        .subscribeOn(Schedulers.trampoline())
//        .test()
//    Mockito.verify(client).requestLocationUpdates(ArgumentMatchers.any(), callbackCaptor.capture(), ArgumentMatchers.any())
//    val callback = callbackCaptor.value
//    val mockLocation = mockLocation(LAT_LNG_0, 10.0f)
//    callback.onLocationResult(LocationResult.create(listOf(mockLocation)))
//    testObserver.assertValueCount(1)
//        .assertValueAt(0, LocationAndHeading(LAT_LNG_0, 10.0f))
//  }
//
//  @Test
//  fun testLocationUpdatesUseLastKnownHeadingWhenHeadingUnknown() {
//    val callbackCaptor = ArgumentCaptor.forClass(LocationCallback::class.java)
//    val testObserver: TestObserver<LocationAndHeading> = locatorUnderTest.observeCurrentLocation(POLL_INTERVAL_MILLIS)
//        .subscribeOn(Schedulers.trampoline())
//        .test()
//    Mockito.verify(client).requestLocationUpdates(ArgumentMatchers.any(), callbackCaptor.capture(), ArgumentMatchers.any())
//    val callback = callbackCaptor.value
//    val locationsEmitted = Arrays.asList(
//        mockLocationWithoutHeading(LAT_LNG_0),  // heading should default to 0.0
//        mockLocation(LAT_LNG_1, 10.0f),  // heading should be updated
//        mockLocationWithoutHeading(LAT_LNG_2),  // heading should be remembered as 10.0f
//        mockLocation(LAT_LNG_3, 15.0f) // heading should be updated
//    )
//    locationsEmitted.forEach(Consumer { location: Location ->
//      callback.onLocationResult(
//          LocationResult.create(listOf(location))
//      )
//    })
//    testObserver.assertValueCount(4)
//        .assertValueAt(0, LocationAndHeading(LAT_LNG_0, 0.0f))
//        .assertValueAt(1, LocationAndHeading(LAT_LNG_1, 10.0f))
//        .assertValueAt(2, LocationAndHeading(LAT_LNG_2, 10.0f))
//        .assertValueAt(3, LocationAndHeading(LAT_LNG_3, 15.0f))
//  }
//
//  @Test
//  fun testLocatorStopsLocationUpdatesWhenSubscriptionIsDisposed() {
//    val observableLocation: Observable<LocationAndHeading> = locatorUnderTest.observeCurrentLocation(POLL_INTERVAL_MILLIS)
//    val disposable = observableLocation.subscribeOn(Schedulers.trampoline()).subscribe()
//    disposable.dispose()
//    Mockito.verify(client).removeLocationUpdates(ArgumentMatchers.any(LocationCallback::class.java))
//  }
//
//  @Test
//  fun testGetLastKnownLocationWhenLocationExists() {
//    val onSuccessCaptor = ArgumentCaptor.forClass(OnSuccessListener::class.java)
//    val mockTask = Mockito.mock(Task::class.java) as Task<Location>
//    Mockito.`when`<Task<*>>(mockTask.addOnSuccessListener(onSuccessCaptor.capture())).thenReturn(mockTask)
//    Mockito.`when`(mockTask.addOnFailureListener(Mockito.any())).thenReturn(mockTask)
//    Mockito.`when`(client!!.lastLocation).thenReturn(mockTask)
//    val location = mockLocation(LAT_LNG_1, 10.0f)
//    val testObserver: TestObserver<LocationAndHeading> = locatorUnderTest.getLastKnownLocation().test()
//    onSuccessCaptor.value.onSuccess(location)
//    testObserver.assertValueCount(1)
//        .assertValueAt(0, LocationAndHeading(LAT_LNG_1, 10.0f))
//    Mockito.verify(client, Mockito.never()).requestLocationUpdates(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
//  }
//
//  @Test
//  fun testGetLastKnownLocationFallsBackToLocationUpdatesWhenLastLocationUnknown() {
//    val onSuccessCaptor = ArgumentCaptor.forClass(OnSuccessListener::class.java)
//    val mockTask = Mockito.mock(Task::class.java) as Task<Location>
//    Mockito.`when`<Task<*>>(mockTask.addOnSuccessListener(onSuccessCaptor.capture())).thenReturn(mockTask)
//    Mockito.`when`(mockTask.addOnFailureListener(Mockito.any())).thenReturn(mockTask)
//    Mockito.`when`(client!!.lastLocation).thenReturn(mockTask)
//    val callbackCaptor = ArgumentCaptor.forClass(LocationCallback::class.java)
//    val mockLocation = mockLocation(LAT_LNG_1, 10.0f)
//
//    // Try to get last known location
//    val testObserver: TestObserver<LocationAndHeading> = locatorUnderTest.getLastKnownLocation().test()
//
//    // Send null as last known location
//    onSuccessCaptor.value.onSuccess(null)
//    // Assert that no value should appear in the observer
//    testObserver.assertValueCount(0)
//
//    // Assert that location updates have started
//    Mockito.verify(client).requestLocationUpdates(ArgumentMatchers.any(), callbackCaptor.capture(), ArgumentMatchers.any())
//    // Get the location update callback and send a location
//    val callback = callbackCaptor.value
//    callback.onLocationResult(LocationResult.create(listOf(mockLocation)))
//
//    // Assert that this location is received
//    testObserver.assertValueCount(1)
//        .assertValueAt(0, LocationAndHeading(LAT_LNG_1, 10.0f))
//  }
//
//  @Test
//  fun testGetLastKnownLocationFallsBackToLocationUpdatesWhenLastLocationErrors() {
//    val onFailureCaptor = ArgumentCaptor.forClass(OnFailureListener::class.java)
//    val mockTask = Mockito.mock(Task::class.java) as Task<Location>
//    Mockito.`when`(mockTask.addOnSuccessListener(Mockito.any())).thenReturn(mockTask)
//    Mockito.`when`(mockTask.addOnFailureListener(onFailureCaptor.capture())).thenReturn(mockTask)
//    Mockito.`when`(client!!.lastLocation).thenReturn(mockTask)
//    val callbackCaptor = ArgumentCaptor.forClass(LocationCallback::class.java)
//    val mockLocation = mockLocation(LAT_LNG_1, 10.0f)
//
//    // Try to get last known location
//    val testObserver: TestObserver<LocationAndHeading> = locatorUnderTest.getLastKnownLocation().test()
//
//    // Send error to failure callback
//    onFailureCaptor.value.onFailure(IOException())
//    // Assert that no value should appear in the observer
//    testObserver.assertValueCount(0)
//
//    // Assert that location updates have started
//    Mockito.verify(client).requestLocationUpdates(ArgumentMatchers.any(), callbackCaptor.capture(), ArgumentMatchers.any())
//    // Get the location update callback and send a location
//    val callback = callbackCaptor.value
//    callback.onLocationResult(LocationResult.create(listOf(mockLocation)))
//
//    // Assert that this location is received
//    testObserver.assertValueCount(1)
//        .assertValueAt(0, LocationAndHeading(LAT_LNG_1, 10.0f))
//  }
//
//  companion object {
//    private val LAT_LNG_0: LatLng = LatLng(0, 0)
//    private val LAT_LNG_1: LatLng = LatLng(0, 1)
//    private val LAT_LNG_2: LatLng = LatLng(0, 2)
//    private val LAT_LNG_3: LatLng = LatLng(0, 3)
//    private const val POLL_INTERVAL_MILLIS = 100
//    private fun mockLocation(latLng: LatLng, heading: Float): Location {
//      val mockLocation = Mockito.mock(Location::class.java)
//      Mockito.`when`(mockLocation.latitude).thenReturn(latLng.getLatitude())
//      Mockito.`when`(mockLocation.longitude).thenReturn(latLng.getLongitude())
//      Mockito.`when`(mockLocation.bearing).thenReturn(heading)
//      Mockito.`when`(mockLocation.hasBearing()).thenReturn(true)
//      return mockLocation
//    }
//
//    private fun mockLocationWithoutHeading(latLng: LatLng): Location {
//      val mockLocation = Mockito.mock(Location::class.java)
//      Mockito.`when`(mockLocation.latitude).thenReturn(latLng.getLatitude())
//      Mockito.`when`(mockLocation.longitude).thenReturn(latLng.getLongitude())
//      Mockito.`when`(mockLocation.bearing).thenReturn(0.0f)
//      Mockito.`when`(mockLocation.hasBearing()).thenReturn(false)
//      return mockLocation
//    }
//  }
//}