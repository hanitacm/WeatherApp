package com.hanitacm.weatherapp.presentation.model

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

data class WeatherSuggestion(val icon: String?,
                             val location: String?,
                             val temperature: String?,
                             val latitude: Double,
                             val longitude: Double) : SearchSuggestion {


  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readDouble(),
      parcel.readDouble())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(icon)
    parcel.writeString(location)
    parcel.writeString(temperature)
    parcel.writeDouble(latitude)
    parcel.writeDouble(longitude)

  }

  override fun describeContents() = 0


  override fun getBody(): String = location ?: ""

  companion object CREATOR : Parcelable.Creator<WeatherSuggestion> {
    override fun createFromParcel(parcel: Parcel) = WeatherSuggestion(parcel)
    override fun newArray(size: Int) = arrayOfNulls<WeatherSuggestion>(size)
  }
}
