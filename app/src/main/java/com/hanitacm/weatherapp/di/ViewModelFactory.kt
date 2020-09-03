package com.hanitacm.weatherapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherViewModel
import com.hanitacm.weatherapp.presentation.viewmodel.ForecastViewModel
import com.hanitacm.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>)
  : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val creator = creators[modelClass]
        ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        ?: throw IllegalArgumentException("Unknown model class: $modelClass")


    try {
      @Suppress("UNCHECKED_CAST")
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}

@Module
abstract class ViewModelBuilder {

  @Binds
  @IntoMap
  @ViewModelKey(WeatherViewModel::class)
  abstract fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(CurrentLocationWeatherViewModel::class)
  abstract fun bindCurrentLocationWeatherViewModel(currentLocationWeatherViewModel: CurrentLocationWeatherViewModel): ViewModel

//  @Binds
//  @IntoMap
//  @ViewModelKey(ForecastViewModel::class)
//  abstract fun bindForecastViewModel(forecastViewModel: ForecastViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)