package com.hanitacm.weatherapp

import android.location.Location
import com.hanitacm.weatherapp.repository.provider.LocationProvider
import io.reactivex.Single

class FakeLocationProvider : LocationProvider {
  override fun requestLocation(): Single<Location> {
    return Single.just(Location("test_provider").apply {
      latitude = 40.0
      longitude = 3.0
      time = System.currentTimeMillis()
    })
  }

}

