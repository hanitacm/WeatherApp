package com.hanitacm.weatherapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import kotlinx.android.synthetic.main.weather_location_item.view.date
import kotlinx.android.synthetic.main.weather_location_item.view.image_weather
import kotlinx.android.synthetic.main.weather_location_item.view.location_clouds
import kotlinx.android.synthetic.main.weather_location_item.view.location_wind
import kotlinx.android.synthetic.main.weather_location_item.view.temperature_max
import kotlinx.android.synthetic.main.weather_location_item.view.temperature_min
import kotlinx.android.synthetic.main.weather_location_item.view.weather_description
import kotlin.properties.Delegates

internal class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
  var items: List<DisplayableWeather> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_location_item, parent, false)


    return WeatherViewHolder(v, parent.context)
  }

  override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
    val mediaItem = items[position]

    holder.bind(mediaItem)

    //holder.itemView.setOnClickListener { listener.invoke(mediaItem) }
  }


  internal inner class WeatherViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
    private val weatherDescription: TextView = itemView.weather_description
    private val imageWeather: ImageView = itemView.image_weather
    private val minTemperature: TextView = itemView.temperature_min
    private val maxTemperature: TextView = itemView.temperature_max
    private val windSpeed: TextView = itemView.location_wind
    private val clouds: TextView = itemView.location_clouds
    private val date: TextView = itemView.date


    fun bind(mediaItem: DisplayableWeather) {
      imageWeather.load(mediaItem.icon)
      weatherDescription.text = mediaItem.longDescription
      maxTemperature.text = String.format(context.getString(R.string.temperature_celsius), mediaItem.temperature_max)
      minTemperature.text = String.format(context.getString(R.string.temperature_celsius), mediaItem.temperature_min)
      date.text = mediaItem.date
      windSpeed.text = String.format(context.getString(R.string.wind_speed), mediaItem.wind)


    }
  }
}
