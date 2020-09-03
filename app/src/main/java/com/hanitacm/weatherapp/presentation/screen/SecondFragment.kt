package com.hanitacm.weatherapp.presentation.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.WeatherApplication
import com.hanitacm.weatherapp.domain.model.ErrorModel
import com.hanitacm.weatherapp.presentation.adapter.WeatherAdapter
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.viewmodel.ForecastViewModel
import com.hanitacm.weatherapp.presentation.viewmodel.ForecastViewState
import kotlinx.android.synthetic.main.activity_searchable.locations
import kotlinx.android.synthetic.main.fragment_first.progressBar
import javax.inject.Inject


class SecondFragment : Fragment() {

  private lateinit var forecastViewModel: ForecastViewModel
  private lateinit var viewManager: RecyclerView.LayoutManager
  private lateinit var viewAdapter: WeatherAdapter

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory


  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_second, container, false)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    (requireActivity().application as WeatherApplication).mainComponent.inject(this)

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupRecyclerView()

    setViewModel()

    loadForecast()
  }

  private fun setupRecyclerView() {
    viewManager = LinearLayoutManager(requireContext())
    viewAdapter = WeatherAdapter()
    locations.adapter = viewAdapter
    locations.layoutManager = viewManager
  }

  private fun setViewModel() {
    forecastViewModel = ViewModelProvider(this, viewModelFactory)[ForecastViewModel::class.java]
  }

  private fun setupObserver() {

    forecastViewModel.viewState.observe(viewLifecycleOwner,
        Observer {
          when (it) {
            is ForecastViewState.Loading -> progressBar.visibility = View.VISIBLE
            is ForecastViewState.ForecastLoaded -> processResponse(it.weather)
            is ForecastViewState.ForecastLoadFailure -> showError(it.error)
          }
        })
  }

  private fun processResponse(weather: List<DisplayableWeather>) {
    TODO("Not yet implemented")
  }

  private fun showError(error: ErrorModel) {
    TODO("Not yet implemented")
  }

  private fun loadForecast() {
    val latitude = arguments?.getDouble("latitude")
    val longitude = arguments?.getDouble("longitude")


    if (latitude != null && longitude != null) {

      forecastViewModel.getForecastWeather(latitude, longitude)

    }

  }


}
