package com.kft.learnkmp.domain

import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.model.Database.SaveList.SavedListDTO
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import kotlinx.coroutines.flow.Flow

interface WeatherApiInterface {

    fun getCurrentWeatherBasedOnLocation(location : Location) : Flow<ResponseHandler<LocationBasedResponse>>
    fun getFutureWeather(location : Location) : Flow<ResponseHandler<ForecastBasedResponse>>
    fun getListOfLocation(location : String) : Flow<ResponseHandler<List<SavedListDTO>>>
    fun getWeatherOfCity(location : String) : Flow<ResponseHandler<LocationBasedResponse>>

}