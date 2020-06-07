package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.repository.UserLocationRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(private val userLocationRepository: UserLocationRepository) {
  fun getUserLocation(): Single<UserLocationDomainModel> {
    return userLocationRepository.getUserLocation()
  }
}