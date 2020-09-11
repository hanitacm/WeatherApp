# WeatherApp  
🚧 In progress  
Typical weather application, show the weather information in your location or wherever you search.
The weather data are supplied by OpenWeather API

## Tech-stack
* Retrofit
* RxJava2
* Dagger2
* Coil 
* Espresso UI Testing
* Mockito
* Jetpack Architecture

   - Navigation 
   - ViewModel
   - LiveData

## Architecture
It implements a Clean Architecture:

* Presentation  
  MVVM pattern 
  - Screens(Activity/Fragments/Views)
  - ViewModels
  - View Data and mappers

* Domain  
  Executes Usecases and defines domain models
  
* Repository  
  Respository Pattern  
  Define data models and mappers  
  Datasources 
   - OpenWeather API
   - Google Service Location Provider
  


