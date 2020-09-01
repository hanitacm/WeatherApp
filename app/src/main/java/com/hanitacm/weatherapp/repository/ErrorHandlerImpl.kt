package com.hanitacm.weatherapp.repository

import com.hanitacm.weatherapp.domain.ErrorHandler
import com.hanitacm.weatherapp.domain.model.ErrorModel
import com.hanitacm.weatherapp.repository.datasource.api.exception.NoNetworkConnectionThrowable
import com.hanitacm.weatherapp.repository.datasource.exceptions.NoLocationAvailableException
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {
  override fun getError(throwable: Throwable): ErrorModel {
    return when (throwable) {
      is NoNetworkConnectionThrowable -> ErrorModel.NoNetworkConnection
      is NoLocationAvailableException -> ErrorModel.NoLocationAvailable
      else -> ErrorModel.GenericError
    }
  }
}