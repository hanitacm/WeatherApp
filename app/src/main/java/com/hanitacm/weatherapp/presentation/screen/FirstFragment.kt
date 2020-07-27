package com.hanitacm.weatherapp.presentation.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.WeatherApplication
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherViewModel
import javax.inject.Inject

const val PERMISSION_REQUEST_LOCATION = 0

class FirstFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

  private lateinit var currentLocationWeatherViewModel: CurrentLocationWeatherViewModel

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_first, container, false)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    (requireActivity().application as WeatherApplication).mainComponent.inject(this)

  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    currentLocationWeatherViewModel = ViewModelProvider(this, viewModelFactory)[CurrentLocationWeatherViewModel::class.java]

    currentLocationWeatherViewModel.getWeather.observe(viewLifecycleOwner,
        Observer { response -> processResponse(response) })
    loadWeather()


  }

  private fun loadWeather() {
    when {
      locationPermissionsAreGranted() -> getWeatherInMyPosition()
      shouldRequestPermissionRationale() -> showUIExplanation(getString(R.string.rational_request_location_permission))
      else -> requestLocationPermission()
    }
  }

  private fun locationPermissionsAreGranted(): Boolean =
      ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
          &&
          ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


  private fun getWeatherInMyPosition() = currentLocationWeatherViewModel.getCurrentLocationWeather()


  private fun shouldRequestPermissionRationale(): Boolean = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

  private fun showUIExplanation(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

  private fun requestLocationPermission() {
    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
  }


  private fun processResponse(response: List<DisplayableWeather>?) {
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    when (requestCode) {
      PERMISSION_REQUEST_LOCATION ->
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getWeatherInMyPosition()
        } else {
          showUIExplanation(getString(R.string.location_permission_denied))
        }
    }
  }
}


