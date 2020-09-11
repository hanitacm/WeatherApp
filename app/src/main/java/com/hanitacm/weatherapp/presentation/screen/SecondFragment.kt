package com.hanitacm.weatherapp.presentation.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.fragment_first.progressBar
import kotlinx.android.synthetic.main.fragment_second.forecast_list
import kotlinx.android.synthetic.main.fragment_second.toolbar
import javax.inject.Inject


class SecondFragment : Fragment() {

  private lateinit var forecastViewModel: ForecastViewModel
  private lateinit var viewManager: RecyclerView.LayoutManager
  private lateinit var viewAdapter: WeatherAdapter

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    setToolBar()
  }

  private fun setToolBar() {
    toolbar.title = arguments?.getString("location")
    (activity as AppCompatActivity).setSupportActionBar(toolbar)

  }

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
    setupObserver()

    loadForecast()
  }

  private fun setupRecyclerView() {
    viewManager = LinearLayoutManager(requireContext())
    viewAdapter = WeatherAdapter()
    forecast_list.adapter = viewAdapter
    forecast_list.layoutManager = viewManager
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
    progressBar.visibility = View.GONE
    viewAdapter.items = weather
  }

  private fun showError(error: ErrorModel) {
    progressBar.visibility = View.GONE

    val errorMessage = processError(error)

    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()

  }

  private fun processError(error: ErrorModel): String {
    return when (error) {
      is ErrorModel.NoNetworkConnection -> getString(R.string.error_no_network_connection)
      else -> getString(R.string.error_generic)
    }
  }


  private fun loadForecast() {
    val latitude = arguments?.getString("latitude")
    val longitude = arguments?.getString("longitude")


    if (latitude != null && longitude != null) {

      forecastViewModel.getForecastWeather(latitude.toDouble(), longitude.toDouble())

    }

  }


}
