package com.hanitacm.weatherapp.di

import com.hanitacm.weatherapp.repository.provider.LocationGoogleServiceProvider
import com.hanitacm.weatherapp.repository.provider.LocationProvider
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

@Module
class TestLocationModule {
  //  @Singleton
  //@Provides
  //fun provideLocationService(): FusedLocationProviderClient = Mockito.mock(FusedLocationProviderClient::class.java)
  //@Singleton
  @Provides
  fun provideUserLocationProvider(): LocationProvider = Mockito.mock(LocationGoogleServiceProvider::class.java)

  //abstract fun provideUserLocationProvider(locationProvider: LocationGoogleServiceProvider): LocationProvider

}