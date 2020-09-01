package com.hanitacm.weatherapp.repository.data.mapper

import android.location.Location
import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import javax.inject.Inject

class UserLocationDomainMapper @Inject constructor() {

  fun mapToDomainModel(userLocation: Location): UserLocationDomainModel = UserLocationDomainModel(userLocation.latitude, userLocation.longitude)
}