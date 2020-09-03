package com.hanitacm.weatherapp.presentation.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.WeatherApplication
import com.hanitacm.weatherapp.di.ViewModelFactory
import com.hanitacm.weatherapp.presentation.adapter.WeatherAdapter
import com.hanitacm.weatherapp.presentation.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.activity_searchable.locations
import javax.inject.Inject


class SecondFragment : Fragment() {

  //private lateinit var forecastViewModel: ForecastViewModel
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

  private fun loadForecast() {
    TODO("Not yet implemented")
  }

  private fun setViewModel() {
    //forecastViewModel = ViewModelProvider(this, viewModelFactory)[ForecastViewModel::class.java]
  }

  private fun setupRecyclerView() {
    viewManager = LinearLayoutManager(requireContext())
    viewAdapter = WeatherAdapter()
    locations.adapter = viewAdapter
    locations.layoutManager = viewManager
  }
}
