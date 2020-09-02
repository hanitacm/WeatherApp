package com.hanitacm.weatherapp.di

import android.content.Context
import com.hanitacm.weatherapp.presentation.screen.FirstFragment
import com.hanitacm.weatherapp.presentation.screen.MapsActivity
import com.hanitacm.weatherapp.presentation.screen.SearchableActivity
import com.hanitacm.weatherapp.presentation.screen.SecondFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelBuilder::class,
      NetworkModule::class,
      LocationModule::class])
interface AppComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance context: Context): AppComponent
  }

  fun inject(activity: SearchableActivity)
  fun inject(activity: MapsActivity)
  fun inject(fragment: FirstFragment)
  fun inject(secondFragment: SecondFragment)

}