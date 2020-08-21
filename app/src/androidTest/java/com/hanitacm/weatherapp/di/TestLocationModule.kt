package com.hanitacm.weatherapp.di

import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import org.mockito.Mockito

@Module
class TestLocationModule {
  @Provides
  fun provideLocationService(): FusedLocationProviderClient = Mockito.mock(FusedLocationProviderClient::class.java)
}