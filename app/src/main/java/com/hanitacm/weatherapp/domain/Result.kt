package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.domain.model.ErrorModel

sealed class Result<T>(private var data: T? = null,
                       private var error: ErrorModel? = null) {

  fun isSuccessfully(): Boolean = data != null
  fun isFailure(): Boolean = error != null

  data class Success<T>(val data: T) : Result<T>(data)

  data class Error<T>(val error: ErrorModel) : Result<T>(error = error)
}