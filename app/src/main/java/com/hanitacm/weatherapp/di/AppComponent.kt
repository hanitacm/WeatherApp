package com.hanitacm.weatherapp.di

import android.content.Context
import com.hanitacm.weatherapp.presentation.screen.SearchableActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelBuilder::class,
                NetworkModule::class])
interface AppComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance context: Context): AppComponent
  }

  fun inject(activity: SearchableActivity)
}