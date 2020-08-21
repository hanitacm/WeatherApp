package com.hanitacm.weatherapp.repository.provider

import android.location.Location
import com.hanitacm.weatherapp.OpenClassOnDebug
import io.reactivex.Single

interface LocationProvider {
  fun requestLocation(): Single<Location>
}