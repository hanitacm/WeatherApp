package com.hanitacm.weatherapp.domain.extensions

import com.hanitacm.weatherapp.domain.ErrorHandler
import com.hanitacm.weatherapp.domain.Result
import io.reactivex.Single

fun <T> Single<T>.toResult(errorHandler: ErrorHandler): Single<Result<T>> =
    this.map { Result.Success(it) as Result<T> }
        .onErrorReturn { Result.Error(errorHandler.getError(it)) }

