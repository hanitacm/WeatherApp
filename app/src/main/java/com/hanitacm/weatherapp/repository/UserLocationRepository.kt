package com.hanitacm.weatherapp.repository

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationRepository @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient) {

  fun getUserLocation(): Single<Location> {

    return Single.create { emitter ->

      val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = TimeUnit.SECONDS.toMillis(10)
      }

      val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
          super.onLocationResult(result)
          result?.let {
            emitter.onSuccess(it.lastLocation)

          }
        }

      }
      fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }

  }
}