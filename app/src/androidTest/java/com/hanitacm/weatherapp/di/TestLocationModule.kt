package com.hanitacm.weatherapp.di

import com.hanitacm.weatherapp.repository.datasource.provider.LocationProvider
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class TestLocationModule {
  @Singleton
  @Provides
  fun provideUserLocationProvider(): LocationProvider = Mockito.mock(LocationProvider::class.java)

}