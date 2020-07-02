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
    if (isLocationPermissionsAreGranted())
      getWeatherInMyPosition()
    else
      requestLocationPermission()
  }

  private fun isLocationPermissionsAreGranted(): Boolean {
    return ContextCompat.checkSelfPermission(requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
  }


  private fun getWeatherInMyPosition() {
    currentLocationWeatherViewModel.getCurrentLocationWeather()
  }

  private fun requestLocationPermission() {
    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
      Toast.makeText(requireContext(), "Location permission is needed to show your position weather", Toast.LENGTH_LONG).show()
    }
    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
  }

  private fun processResponse(response: List<DisplayableWeather>?) {
    TODO("Not yet implemented")
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (requestCode == PERMISSION_REQUEST_LOCATION) {
      if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        getWeatherInMyPosition()
      } else {
        // Permission request was denied.
        //layout.showSnackbar(R.string.camera_permission_denied, Snackbar.LENGTH_SHORT)
      }
    }
  }

}
