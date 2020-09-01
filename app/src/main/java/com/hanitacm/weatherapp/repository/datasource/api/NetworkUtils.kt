package com.hanitacm.weatherapp.repository.datasource.api

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.hanitacm.weatherapp.OpenClassOnDebug
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenClassOnDebug
class NetworkUtils @Inject constructor(private val connectivityManager: ConnectivityManager) {

  @SuppressLint("MissingPermission")
  fun isOnline(): Boolean {
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

    return activeNetwork?.isConnectedOrConnecting == true
  }
}