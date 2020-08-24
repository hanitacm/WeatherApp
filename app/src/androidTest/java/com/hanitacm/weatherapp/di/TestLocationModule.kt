package com.hanitacm.weatherapp.di

import com.hanitacm.weatherapp.FakeLocationProvider
import com.hanitacm.weatherapp.repository.provider.LocationProvider
import dagger.Module
import dagger.Provides

@Module
class TestLocationModule {
  //  @Singleton
  //@Provides
  //fun provideLocationService(): FusedLocationProviderClient = Mockito.mock(FusedLocationProviderClient::class.java)
  //@Singleton
  @Provides
  fun provideUserLocationProvider(): LocationProvider = FakeLocationProvider() // Mockito.mock(LocationGoogleServiceProvider::class.java)
  //abstract fun provideUserLocationProvider(locationProvider: LocationGoogleServiceProvider): LocationProvider

}