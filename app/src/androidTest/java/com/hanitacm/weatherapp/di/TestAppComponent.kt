package com.hanitacm.weatherapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelBuilder::class,
  NetworkModule::class,
  TestLocationModule::class])
interface TestAppComponent : AppComponent