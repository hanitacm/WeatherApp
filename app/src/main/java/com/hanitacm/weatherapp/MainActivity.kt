package com.hanitacm.weatherapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import com.hanitacm.weatherapp.repository.WeatherRepository
import com.hanitacm.weatherapp.repository.api.RetrofitBase
import com.hanitacm.weatherapp.repository.api.WeatherApi
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.async
import kotlinx.android.synthetic.main.activity_main.location


class MainActivity : AppCompatActivity() {

  private val subscription = CompositeDisposable()
  private lateinit var weatherViewModel: WeatherViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val weatherRepository = WeatherRepository(WeatherApi(RetrofitBase()))
    weatherViewModel =
        ViewModelProviders.of(
            this,
            WeatherViewModel.WeatherViewModelFactory(GetWeatherUseCase(weatherRepository))
        )[WeatherViewModel::class.java]

    async.setOnClickListener {
      weatherViewModel.getWeatherData(location.text.toString())

    }
  }

  override fun onResume() {
    super.onResume()

//    subscription.add(weatherViewModel.getWeatherData()
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(this::updateView,
//            this::handleError))
  }

  override fun onPause() {
    subscription.clear()
    super.onPause()
  }
}
