package com.hanitacm.weatherapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.presentation.model.DisplayableWeather
import kotlinx.android.synthetic.main.weather_location_item.view.location_text
import kotlinx.android.synthetic.main.weather_location_item.view.weather_description
import kotlin.properties.Delegates

internal  class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
  var items: List<DisplayableWeather> by Delegates.observable(emptyList()) { p, old, new -> notifyDataSetChanged() }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_location_item, null)


    return WeatherViewHolder(v)
  }

  override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
    val mediaItem = items[position]

    holder.bind(mediaItem)

    //holder.itemView.setOnClickListener { listener.invoke(mediaItem) }
  }


 internal inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val location : TextView = itemView.location_text
    val weatherDescription :TextView = itemView.weather_description



    fun bind(mediaItem: DisplayableWeather) {

      location.text = mediaItem.temperature.toString()
      weatherDescription.text =mediaItem.description
    }
  }
}
