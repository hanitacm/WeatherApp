package com.hanitacm.weatherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hanitacm.weatherapp.repository.provider.LocationGoogleServiceProvider
import com.hanitacm.weatherapp.repository.provider.LocationProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class LocationModule {
  @Singleton
  @Binds
  abstract fun provideUserLocationProvider(locationGoogleServiceProvider: LocationGoogleServiceProvider): LocationProvider

  companion object {
    @Provides
    fun provideLocationService(context: Context): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

  }
}