package com.hanitacm.weatherapp.test

import android.app.Instrumentation
import android.location.Location
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.hanitacm.weatherapp.R
import com.hanitacm.weatherapp.WeatherApplication
import com.hanitacm.weatherapp.di.TestAppComponent
import com.hanitacm.weatherapp.presentation.screen.MainActivity
import com.hanitacm.weatherapp.repository.datasource.api.NetworkUtils
import com.hanitacm.weatherapp.repository.datasource.provider.LocationProvider
import com.hanitacm.weatherapp.utils.fromJson
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class WeatherMainFragmentShould {
  @Inject
  lateinit var locationProvider: LocationProvider

  @Inject
  lateinit var networkUtils: NetworkUtils

  private val webServer = MockWebServer()

  @get:Rule
  val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

  @get:Rule
  val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")


  private lateinit var resource: IdlingResource
  private lateinit var app: WeatherApplication


  @Before
  fun setUp() {
    val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    app = instrumentation.targetContext.applicationContext as WeatherApplication
    val component: TestAppComponent = app.mainComponent as TestAppComponent

    component.inject(this)


    resource = OkHttp3IdlingResource.create("OkHttp", component.okHttpClient())
    IdlingRegistry.getInstance().register(resource)


    webServer.start(8080)

  }

  @Test
  fun show_weather_data_on_screen() {

    webServer.enqueue(MockResponse()
        .setResponseCode(200)
        .fromJson("weatherData.json"))

    whenever(networkUtils.isOnline()).thenReturn(true)

    whenever(locationProvider.requestLocation()).thenReturn(Single.just(Location("test_provider").apply {
      latitude = 38.97
      longitude = -0.18
      time = System.currentTimeMillis()
    }))


    //"{\"coord\":{\"lon\":115.66,\"lat\":-33.35},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"base\":"stations","main":{"temp":11.11,"feels_like":6.34,"temp_min":11.11,"temp_max":11.11,"pressure":1012,"humidity":54},"visibility":10000,"wind":{"speed":4.46,"deg":204},"clouds":{"all":77},"dt":1597748559,"sys":{"type":3,"id":2011706,"country":"AU","sunrise":1597704741,"sunset":1597744223},"timezone":28800,"id":7839476,"name":"Bunbury","cod":200}"

    activityTestRule.launchActivity(null)

    onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    onView(withId(R.id.temp)).check(matches((withText(app.getString(R.string.temperature_celsius, "29")))))
    onView(withId(R.id.status)).check(matches((withText("Clear"))))
    onView(withId(R.id.humidity)).check(matches((withText("5%"))))
    onView(withId(R.id.wind)).check(matches((withText("0.89m/s"))))
    onView(withId(R.id.pressure)).check(matches((withText("1012hPa"))))

  }

  @Test
  fun show_error_message_if_there_is_a_error() {

    whenever(networkUtils.isOnline()).thenReturn(false)

    activityTestRule.launchActivity(null)


    onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    onView(withText(R.string.error_no_network_connection))
        .inRoot(withDecorView(not(activityTestRule.activity.window.decorView)))
        .check(matches(isDisplayed()))

  }

  @After
  fun tearDown() {

    webServer.shutdown()
    IdlingRegistry.getInstance().unregister(resource)


  }
}
