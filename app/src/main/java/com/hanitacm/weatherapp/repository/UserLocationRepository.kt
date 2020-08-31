package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.model.UserLocationDomainModel
import com.hanitacm.weatherapp.repository.data.mapper.UserLocationDomainMapper
import com.hanitacm.weatherapp.repository.datasource.provider.LocationProvider
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationRepository @Inject constructor(private val locationProvider: LocationProvider, private val mapper: UserLocationDomainMapper) {

  fun getUserLocation(): Single<UserLocationDomainModel> {

    return locationProvider.requestLocation().map { mapper.mapToDomainModel(it) }

  }


}

