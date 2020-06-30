package com.hanitacm.weatherapp.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherViewModel
import androidx.lifecycle.Observer
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import javax.inject.Inject


class FirstFragment : Fragment() {
  private lateinit var currentLocationWeatherViewModel: CurrentLocationWeatherViewModel

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_first, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    currentLocationWeatherViewModel = ViewModelProvider(this, viewModelFactory)[CurrentLocationWeatherViewModel::class.java]

    currentLocationWeatherViewModel.getWeather.observe(viewLifecycleOwner,
        Observer { response -> processResponse(response) })
  }

  private fun processResponse(response: List<DisplayableWeather>?) {
    TODO("Not yet implemented")
  }
}
