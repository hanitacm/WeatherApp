package com.hanitacm.weatherapp.presentation.screen

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.WeatherApplication
import com.hanitacm.weatherapp.presentation.adapter.WeatherAdapter
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import com.hanitacm.weatherapp.presentation.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_searchable.locations
import javax.inject.Inject


class SearchableActivity : AppCompatActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  //private val weatherViewModel by viewModels<WeatherViewModel> { viewModelFactory }

  private lateinit var viewAdapter: WeatherAdapter
  private lateinit var viewManager: RecyclerView.LayoutManager

  override fun onCreate(savedInstanceState: Bundle?) {
    (application as WeatherApplication).mainComponent.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_searchable)

    handleIntent(intent)

    //val weatherApi = WeatherApi(RetrofitBase())
    //val mapper = WeatherDataDomainMapper()
    //val weatherRepository = WeatherRepository(weatherApi, mapper)


    viewManager = LinearLayoutManager(this)
    viewAdapter = WeatherAdapter()
    locations.adapter = viewAdapter
    locations.layoutManager = viewManager

    val weatherViewModel = ViewModelProviders.of(this, viewModelFactory)[WeatherViewModel::class.java]

    weatherViewModel.getWeather.observe(this,
        Observer<List<DisplayableWeather>> { response ->
          processResponse(response)
        })
  }


  override fun onNewIntent(intent: Intent) {
    setIntent(intent)
    handleIntent(intent)
  }

  private fun handleIntent(intent: Intent) {
    if (Intent.ACTION_SEARCH == intent.action) {
      intent.getStringExtra(SearchManager.QUERY)?.also { query ->
        getWeatherLocations(query)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.menu, menu)
    menu.findItem(R.id.app_bar_search).actionView

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    (menu.findItem(R.id.app_bar_search).actionView as SearchView)
        .apply {
          setSearchableInfo(searchManager.getSearchableInfo(componentName))

          setOnCloseListener {
            viewAdapter.items = emptyList()
            false
          }

          setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
              return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
              if (newText.isNullOrEmpty()) viewAdapter.items = emptyList()
              return false
            }
          })

        }

    return true
  }

  private fun getWeatherLocations(location: String?) {
    //if (!location.isNullOrBlank())
     // weatherViewModel.loadWeather(location)
  }


  private fun processResponse(response: List<DisplayableWeather>?) {
    if (response != null) {
      viewAdapter.items = response
    }
  }
}
