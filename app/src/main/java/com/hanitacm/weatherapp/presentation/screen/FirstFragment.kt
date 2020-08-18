package com.hanitacm.weatherapp.presentation.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.WeatherApplication
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.model.WeatherSuggestion
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherState
import com.hanitacm.weatherapp.presentation.viewmodel.CurrentLocationWeatherViewModel
import kotlinx.android.synthetic.main.fragment_first.date
import kotlinx.android.synthetic.main.fragment_first.floating_search_view
import kotlinx.android.synthetic.main.fragment_first.humidity
import kotlinx.android.synthetic.main.fragment_first.pressure
import kotlinx.android.synthetic.main.fragment_first.progressBar
import kotlinx.android.synthetic.main.fragment_first.status
import kotlinx.android.synthetic.main.fragment_first.temp
import kotlinx.android.synthetic.main.fragment_first.weather_icon
import kotlinx.android.synthetic.main.fragment_first.wind
import java.util.Date
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

    setViewModel()

    setupObservers()
    setupFloatingSearchView()

    loadWeather()

  }

  private fun setViewModel() {
    currentLocationWeatherViewModel = ViewModelProvider(this, viewModelFactory)[CurrentLocationWeatherViewModel::class.java]
  }

  private fun setupObservers() {

    currentLocationWeatherViewModel.viewState.observe(viewLifecycleOwner,
        Observer {
          when (it) {
            CurrentLocationWeatherState.Loading -> progressBar.visibility = View.VISIBLE
            is CurrentLocationWeatherState.LocationSuggestionsLoaded -> showSuggestions(it.weatherSuggestions)
            is CurrentLocationWeatherState.WeatherLoaded -> processResponse(it.weather)
            is CurrentLocationWeatherState.WeatherLoadFailure -> showError(it.errorMessage)
          }
        })

    //currentLocationWeatherViewModel.getWeather.observe(viewLifecycleOwner,
    //    Observer { response -> processResponse(response) })


    //currentLocationWeatherViewModel.getWeatherSuggestions.observe(viewLifecycleOwner,
    //    Observer { suggestions -> showSuggestions(suggestions) })
  }

  private fun showError(errorMessage: String) {
    progressBar.visibility = View.GONE
    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
  }


  private fun setupFloatingSearchView() {

    floating_search_view.setOnQueryChangeListener { oldQuery, newQuery ->
      if (oldQuery.isBlank() && newQuery.isBlank()) {
        floating_search_view.clearSuggestions()
      } else {
        currentLocationWeatherViewModel.loadLocationSuggestions(newQuery)
      }
    }

    floating_search_view.setOnBindSuggestionCallback { _, leftIcon, textView, item, _ ->
      val suggestion: WeatherSuggestion = item as WeatherSuggestion

      leftIcon.load(suggestion.icon)
      textView.text = (Html.fromHtml("${suggestion.location}<br/> ${suggestion.temperature}ºC"))
    }

    floating_search_view.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
      override fun onSearchAction(currentQuery: String?) {
      }

      override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
        searchSuggestion?.let {
          it as WeatherSuggestion
          currentLocationWeatherViewModel.getWeather(it.latitude, it.longitude)
        }
      }
    })
  }

  private fun showSuggestions(suggestions: List<WeatherSuggestion>?) {
    if (!suggestions.isNullOrEmpty()) {
      floating_search_view.swapSuggestions(suggestions)
    }
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
    floating_search_view.clearSuggestions()
    progressBar.visibility = View.GONE
    if (!response.isNullOrEmpty()) {
      response.first().let {
        floating_search_view.setSearchBarTitle(it.location)
        date.text = String.format("%tA %<tB %<td %<tr", Date())
        temp.text = getString(R.string.temperature_celsius, it.temperature)
        status.text = it.description
        humidity.text = it.humidity.toString()
        weather_icon.load(it.icon)
        wind.text = it.wind
        pressure.text = it.pressure
      }
    }
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


