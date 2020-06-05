package com.hanitacm.weatherapp.repository

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.hanitacm.weatherapp.domain.UserLocationDomainModel
import com.hanitacm.weatherapp.repository.data.mapper.UserLocationDomainMapper
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationRepository @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient, private val mapper: UserLocationDomainMapper) {

  fun getUserLocation(): Single<UserLocationDomainModel> {
    return Single.create { emitter ->
      fusedLocationProviderClient.lastLocation.addOnSuccessListener { taskLocation ->
        if (taskLocation != null) {
          emitter.onSuccess(mapper.mapToDomainModel(taskLocation))
        } else {
          emitter.onError(IllegalStateException("Location is null"))
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

  private fun createLocationRequest(): LocationRequest {
    return LocationRequest().apply {
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      interval = TimeUnit.SECONDS.toMillis(10)
    }
  }

  private fun stop(locationCallback: LocationCallback) {
    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
  }


}

