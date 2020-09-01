package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.domain.model.ErrorModel

interface ErrorHandler {
  fun getError(throwable: Throwable): ErrorModel

}