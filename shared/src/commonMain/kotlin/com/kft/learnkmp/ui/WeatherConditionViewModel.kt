package com.kft.learnkmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.domain.WeatherApiInterface
import com.kft.learnkmp.model.Database.AppDatabase
import com.kft.learnkmp.model.Database.SaveList.SavedListDTO
import com.kft.learnkmp.model.Database.WatchList.WatchListDTO
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherConditionViewModel (val appDatabase: AppDatabase, val weatherApiInterface : WeatherApiInterface) : ViewModel(){

    private val _currentWeatherOfLocation = MutableStateFlow<ResponseHandler<LocationBasedResponse>>(ResponseHandler.Loading)
    val currentWeatherOfLocation = _currentWeatherOfLocation.asStateFlow()

    private val _futureWeatherOfLocation = MutableStateFlow<ResponseHandler<ForecastBasedResponse>>(ResponseHandler.Loading)
    val futureWeatherOfLocation = _futureWeatherOfLocation.asStateFlow()


    private var _isSaved = MutableStateFlow<WatchListDTO?>(null)
    val isSaved = _isSaved.asStateFlow()



    fun getFutureWeather(location: Location){

        viewModelScope.launch {
            weatherApiInterface.getFutureWeather(location).collect{
                _futureWeatherOfLocation.value = it
            }
        }
    }


    fun addLocationToSavedList(it : LocationBasedResponse){
        val savedListDTO = SavedListDTO(
            name = it.name!!,
            country = it.sys?.country!!,
            lat = it.coord?.lat!!,
            lon = it.coord.lon!!,
            state = it.name
        )

        viewModelScope.launch(Dispatchers.IO){
            print("My App Database ")
            try{
                appDatabase.getSavedListDao().deleteFromSavedList(savedListDTO.lat, savedListDTO.lon)
                appDatabase.getSavedListDao().insertIntoSavedList(savedListDTO)
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchSavedState(lat : Double, lon : Double){
        viewModelScope.launch {
            appDatabase.getWatchListDao().getCurrentWatchListState(lat, lon).collectLatest {
                _isSaved.value = it
            }
        }
    }

    fun addWatchList(watchListDTO: WatchListDTO){
        viewModelScope.launch {


            if(watchListDTO.id != 0){
                appDatabase.getWatchListDao().deleteFromWatchList(watchListDTO)
            }
            else{
                appDatabase.getWatchListDao().insertIntoWatchList(watchListDTO)
            }

        }
    }




    fun getCurrentWeather(location: String){

        viewModelScope.launch {
            weatherApiInterface.getWeatherOfCity(location).collect{
                _currentWeatherOfLocation.value = it
                if(it is ResponseHandler.Success){
                    fetchSavedState(it.data.coord?.lat!!, it.data.coord.lon!!)
                    addLocationToSavedList(it.data)
                    getFutureWeather(Location(it.data.coord?.lat!!, it.data.coord.lon!!))
                }
            }
        }
    }

    fun getCurrentWeather(location: Location){

        viewModelScope.launch {
            weatherApiInterface.getCurrentWeatherBasedOnLocation(location).collect{
                _currentWeatherOfLocation.value = it
                if(it is ResponseHandler.Success){
                    fetchSavedState(it.data.coord?.lat!!, it.data.coord.lon!!)
                    addLocationToSavedList(it.data)
                    getFutureWeather(Location(it.data.coord?.lat!!, it.data.coord.lon!!))
                }
            }
        }
    }




}