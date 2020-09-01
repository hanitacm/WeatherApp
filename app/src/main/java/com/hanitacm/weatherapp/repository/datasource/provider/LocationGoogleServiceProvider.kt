package com.hanitacm.weatherapp.repository.datasource.provider

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.hanitacm.weatherapp.repository.datasource.exceptions.NoLocationAvailableException
import io.reactivex.Single
import javax.inject.Inject

class LocationGoogleServiceProvider @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient) : LocationProvider {
  @SuppressLint("MissingPermission")
  override fun requestLocation(): Single<Location> {
    return Single.create { emitter ->

      fusedLocationProviderClient.lastLocation
          .addOnSuccessListener { taskLocation ->
            if (taskLocation != null)
              emitter.onSuccess(taskLocation)
            else
              emitter.onError(NoLocationAvailableException("Google Service couldn't get location"))
          }
    }
  }
}

//fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
//OR
//      fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//        location?.let {
//          emitter.onSuccess(mapper.mapToDomainModel(it))
//        }
//            ?: emitter.onError(Exception())

//      }
// stop(locationCallback)

// }
//}
// }

//  private fun createLocationRequest(): LocationRequest {
//    return LocationRequest().apply {
//      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//      interval = TimeUnit.SECONDS.toMillis(10)
//    }
//  }
//
//  private fun stop(locationCallback: LocationCallback) {
//    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//  }
