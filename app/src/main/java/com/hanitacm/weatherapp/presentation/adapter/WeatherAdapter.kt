package com.hanitacm.weatherapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import kotlinx.android.synthetic.main.weather_location_item.view.image_weather
import kotlinx.android.synthetic.main.weather_location_item.view.location_temperature
import kotlinx.android.synthetic.main.weather_location_item.view.location_text
import kotlinx.android.synthetic.main.weather_location_item.view.weather_description
import kotlin.properties.Delegates

internal class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
  var items: List<DisplayableWeather> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_location_item, parent)


    return WeatherViewHolder(v)
  }

  override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
    val mediaItem = items[position]

    holder.bind(mediaItem)

    //holder.itemView.setOnClickListener { listener.invoke(mediaItem) }
  }


  internal inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val location: TextView = itemView.location_text
    private val weatherDescription: TextView = itemView.weather_description
    private val temperature: TextView = itemView.location_temperature
    private val imageWeather: ImageView = itemView.image_weather


    fun bind(mediaItem: DisplayableWeather) {
      imageWeather.load(itemView.context.getString(R.string.weather_icon_url,mediaItem.icon))
      location.text = mediaItem.location
      weatherDescription.text = mediaItem.description
      temperature.text = itemView.context.getString(R.string.listable_temperature, mediaItem.temperature, mediaItem.temperature_min, mediaItem.temperature_max)
    }
  }
}
