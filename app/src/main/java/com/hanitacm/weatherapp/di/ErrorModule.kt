package com.hanitacm.weatherapp.di

import com.hanitacm.weatherapp.domain.ErrorHandler
import com.hanitacm.weatherapp.repository.ErrorHandlerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ErrorModule {
  @Singleton
  @Binds
  abstract fun provideErrorHandler(errorHandler: ErrorHandlerImpl): ErrorHandler
}