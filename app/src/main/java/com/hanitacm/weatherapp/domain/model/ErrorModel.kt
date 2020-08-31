package com.hanitacm.weatherapp.domain.model

sealed class ErrorModel : Throwable() {
  object NoNetworkConnection : ErrorModel()
  object NoLocationAvailable : ErrorModel()
  object GenericError : ErrorModel()
}