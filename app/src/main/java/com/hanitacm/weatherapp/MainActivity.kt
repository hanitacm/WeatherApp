package com.hanitacm.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.sync

class MainActivity : AppCompatActivity() {

  private lateinit var presenter: mainActivityPresenter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    presenter = mainActivityPresenter()
    sync.setOnClickListener { presenter.getWeatherSync() }
  }


}
