package com.hanitacm.weatherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

  @Provides
  fun provideLocationService(context: Context): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
}