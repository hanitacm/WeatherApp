package com.hanitacm.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.location
import kotlinx.android.synthetic.main.activity_main.sync

class MainActivity : AppCompatActivity() {

  private lateinit var presenter: MainActivityPresenter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    presenter = MainActivityPresenter(GetWeatherUseCase())
    sync.setOnClickListener { presenter.getWeatherSync(location.text.toString()) }
  }


}
