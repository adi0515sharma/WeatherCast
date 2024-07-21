package com.kft.learnkmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.Utils.getLocationAccess
import com.kft.learnkmp.domain.WeatherApiInterface
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.WeatherApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class Next5DaysForecastViewModel (val weatherApiInterface : WeatherApiInterface) : ViewModel() {

    private val _futureWeatherOfLocation = MutableStateFlow<ResponseHandler<ForecastBasedResponse>>(ResponseHandler.Loading)
    val futureWeatherOfLocation = _futureWeatherOfLocation.asStateFlow()


    fun getFutureWeather(location: Location){

        viewModelScope.launch {
            weatherApiInterface.getFutureWeather(location).collect{
                _futureWeatherOfLocation.value = it
            }
        }
    }
}