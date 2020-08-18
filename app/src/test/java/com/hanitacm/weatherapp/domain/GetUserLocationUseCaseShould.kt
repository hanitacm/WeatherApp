package com.hanitacm.weatherapp.domain

import com.hanitacm.weatherapp.repository.UserLocationRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class GetUserLocationUseCaseShould {
  @Mock
  private lateinit var userLocationRepository: UserLocationRepository

  @InjectMocks
  private lateinit var getUserLocationUseCase: GetUserLocationUseCase

  @BeforeEach
  fun setUp() {
    MockitoAnnotations.initMocks(this)
  }

  @Test
  fun `call user location repository`() {

    getUserLocationUseCase.getUserLocation()

    verify(userLocationRepository).getUserLocation()
  }
}