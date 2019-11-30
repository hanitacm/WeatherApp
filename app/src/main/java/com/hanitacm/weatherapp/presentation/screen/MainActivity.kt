package com.hanitacm.weatherapp.presentation.screen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.presentation.viewmodel.WeatherViewModel
import io.reactivex.disposables.CompositeDisposable


class MainActivity : AppCompatActivity() {

  private val subscription = CompositeDisposable()
  private lateinit var weatherViewModel: WeatherViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    //val weatherRepository = WeatherRepository(WeatherApi(RetrofitBase()))

//    weatherViewModel =
//        ViewModelProviders.of(
//            this,
//            WeatherViewModel.WeatherViewModelFactory(GetWeatherUseCase(weatherRepository))
//        )[WeatherViewModel::class.java]

//    async.setOnClickListener {
//      weatherViewModel.loadWeather(location.text.toString())
//
//    }


//    weatherViewModel.getWeather.observe(this,
//        Observer<DisplayableWeather> { response ->
//          processResponse(response)
//        })


  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {

    return when (item?.itemId) {
      R.id.search_location -> {
        startActivity(Intent(this, SearchableActivity::class.java))
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }


  //  private fun processResponse(response: DisplayableWeather?) {
//    if (response != null) {
//      weatherDescription.text = response.description
//      temperature.text = response.temperature.toString()
//      humidity.text = response.humidity.toString()
//    }
//  }
}
