package com.hanitacm.weatherapp.repository.data

import com.google.gson.annotations.SerializedName

data class WeatherLocationData(
    @SerializedName("message")
    val message: String,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<WeatherData>
)

