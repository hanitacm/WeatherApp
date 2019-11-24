package com.hanitacm.weatherapp.presentation.screen

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.presentation.adapter.WeatherAdapter
import com.hanitacm.weatherapp.domain.GetWeatherUseCase
import com.hanitacm.weatherapp.presentation.viewmodel.WeatherViewModel
import com.hanitacm.weatherapp.repository.WeatherRepository
import com.hanitacm.weatherapp.repository.api.RetrofitBase
import com.hanitacm.weatherapp.repository.api.WeatherApi
import com.hanitacm.weatherapp.repository.data.WeatherDataDomainMapper

class SearchableActivity : AppCompatActivity() {
  private lateinit var weatherViewModel: WeatherViewModel
  private lateinit var viewAdapter: WeatherAdapter
  private lateinit var viewManager: RecyclerView.LayoutManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_searchable)

    val weatherApi = WeatherApi(RetrofitBase())
    val mapper = WeatherDataDomainMapper()
    val weatherRepository = WeatherRepository(weatherApi, mapper)


    viewManager = LinearLayoutManager(this)
    viewAdapter = WeatherAdapter()

    weatherViewModel =
        ViewModelProviders.of(
            this,
            WeatherViewModel.WeatherViewModelFactory(GetWeatherUseCase(weatherRepository))
        )[WeatherViewModel::class.java]

    if (Intent.ACTION_SEARCH == intent.action) {
      intent.getStringExtra(SearchManager.QUERY)?.also { query ->
        weatherViewModel.getWeather.observe(this,
            Observer<List<DisplayableWeather>> { response ->
              processResponse(response)
            })
      }
    }
  }

  private fun processResponse(response: List<DisplayableWeather>?) {
    if (response != null) {
      viewAdapter.items = response
//      weatherDescription.text = response.description
//      temperature.text = response.temperature.toString()
//      humidity.text = response.humidity.toString()
    }
  }
}
