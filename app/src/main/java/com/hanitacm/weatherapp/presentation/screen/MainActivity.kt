package com.hanitacm.weatherapp.presentation.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hanitacm.weatherapp.R
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)


  }

}
