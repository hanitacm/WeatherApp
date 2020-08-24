package com.hanitacm.weatherapp

import android.app.Instrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.hanitacm.weatherapp.di.TestAppComponent
import com.hanitacm.weatherapp.presentation.screen.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

  private val webServer = MockWebServer()

  //@get:Rule
  //val resource = OkHttpIdlingResourceRule(resource)
  @get:Rule
  val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

  //@Inject
  //lateinit var locationProvider: LocationProvider
  private lateinit var resource: IdlingResource


  @Before
  fun setUp() {
    val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
    val app: WeatherApplication = instrumentation.targetContext.applicationContext as WeatherApplication
    val component: TestAppComponent = app.mainComponent as TestAppComponent

    component.inject(this)

    resource = component.idlingResource()
    IdlingRegistry.getInstance().register(resource)


    //MockitoAnnotations.initMocks(this)
    webServer.start(8080)


  }

  @Test
  fun useAppContext() {
//    whenever(locationProvider.requestLocation()).thenReturn(Single.just(Location("test_provider").apply {
//      latitude = 38.97
//      longitude = -0.18
//      time = System.currentTimeMillis()
//    }))

    webServer.enqueue(MockResponse().setResponseCode(200).setBody("{coord\":{\"lon\":-0.18,\"lat\":38.97},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":28.51,\"feels_like\":24.53,\"temp_min\":27.22,\"temp_max\":28.89,\"pressure\":1012,\"humidity\":5},\"visibility\":10000,\"wind\":{\"speed\":0.89,\"deg\":45,\"gust\":1.34},\"clouds\":{\"all\":3},\"dt\":1597748537,\"sys\":{\"type\":3,\"id\":2000678,\"country\":\"ES\",\"sunrise\":1597727839,\"sunset\":1597776720},\"timezone\":7200,\"id\":2517367,\"name\":\"Gandia\",\"cod\":200}"))

    //"{\"coord\":{\"lon\":115.66,\"lat\":-33.35},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"base\":"stations","main":{"temp":11.11,"feels_like":6.34,"temp_min":11.11,"temp_max":11.11,"pressure":1012,"humidity":54},"visibility":10000,"wind":{"speed":4.46,"deg":204},"clouds":{"all":77},"dt":1597748559,"sys":{"type":3,"id":2011706,"country":"AU","sunrise":1597704741,"sunset":1597744223},"timezone":28800,"id":7839476,"name":"Bunbury","cod":200}"

    activityTestRule.launchActivity(null)



    onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
  }

  @After
  fun tearDown() {
    webServer.shutdown()
    IdlingRegistry.getInstance().unregister(resource)


  }
}
