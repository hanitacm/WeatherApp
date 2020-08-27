package com.hanitacm.weatherapp.di

import com.hanitacm.weatherapp.FakeLocationProvider
import com.hanitacm.weatherapp.repository.provider.LocationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestLocationModule {
  @Singleton
  @Provides
  fun provideUserLocationProvider(): LocationProvider = FakeLocationProvider()

}