package com.hanitacm.weatherapp.di

import androidx.test.espresso.IdlingResource
import com.hanitacm.weatherapp.ExampleInstrumentedTest
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Singleton
@Component(modules = [ViewModelBuilder::class,
  TestNetworkModule::class,
  TestLocationModule::class])
interface TestAppComponent : AppComponent {

  fun inject(firstFragmentTest: ExampleInstrumentedTest)
  fun okHttpClient(): OkHttpClient
  fun idlingResource(): IdlingResource
}