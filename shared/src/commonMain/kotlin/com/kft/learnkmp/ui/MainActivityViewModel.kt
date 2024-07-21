package com.kft.learnkmp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.Utils.getLocationAccess
import com.kft.learnkmp.domain.WeatherApiInterface
import com.kft.learnkmp.model.Database.AppDatabase
import com.kft.learnkmp.model.Database.SaveList.SavedListDTO
import com.kft.learnkmp.model.Database.WatchList.WatchListDTO
import com.kft.learnkmp.model.Database.getDatabaseInstance
import com.kft.learnkmp.model.LocationBasedResponse.Coord
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import com.kft.learnkmp.model.WatchEnum
import com.kft.learnkmp.model.WatchItemUI
import com.kft.learnkmp.model.WeatherApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class MainActivityViewModel (val weatherApiInterface : WeatherApiInterface, val appDatabase: AppDatabase): ViewModel() {


    private val _currentWeatherOfLocation = MutableStateFlow<ResponseHandler<LocationBasedResponse>>(ResponseHandler.Loading)
    val currentWeatherOfLocation = _currentWeatherOfLocation.asStateFlow()

    private val _futureWeatherOfLocation = MutableStateFlow<ResponseHandler<ForecastBasedResponse>>(ResponseHandler.Loading)
    val futureWeatherOfLocation = _futureWeatherOfLocation.asStateFlow()

    private val _lastSearchHistory = MutableStateFlow<List<SavedListDTO>>(emptyList())
    val lastSearchHistoryFromDB = _lastSearchHistory.asStateFlow()

    private val _getSearchHistory = MutableStateFlow<List<SavedListDTO>>(emptyList())
    val getSearchHistory = _getSearchHistory.asStateFlow()


    private val _lastWatchList = MutableStateFlow<List<WatchItemUI>>(emptyList())
    val lastWatchList = _lastWatchList.asStateFlow()


    var currentLocation : Location? = null

    init{

        viewModelScope.launch {

            getLastSearchHistory()
            fetchLastWatchList()

            fetchHomePageData()

        }
    }

    fun fetchHomePageData(){
        viewModelScope.launch(Dispatchers.IO){
            _currentWeatherOfLocation.value = ResponseHandler.Loading
            currentLocation = getLocationAccess().getCurrentLocation()

            if(currentLocation!=null){
                getCurrentWeather(currentLocation)
                getFutureWeather(currentLocation)
            }
        }

    }

    fun getCurrentWeather(location: Location?){
        if(location == null) {
            return
        }
        viewModelScope.launch {
            weatherApiInterface.getCurrentWeatherBasedOnLocation(location).collect{
                _currentWeatherOfLocation.value = it
            }
        }
    }




    fun getLastSearchHistory() {
        viewModelScope.launch {
            appDatabase.getSavedListDao().getAllSavedList().collectLatest {
                _lastSearchHistory.value = it
            }
        }
    }

    fun fetchLastWatchList(){
        observeWatchList()
        viewModelScope.launch {
            appDatabase.getWatchListDao().getAllWatchList().collectLatest {

                if(it != null){
                    val previousList = _lastWatchList.value
                    val newLocationList = mutableListOf<WatchItemUI>()
                    for(i in it){
                        var isAvail = false
                        for(item  in previousList){
                            if(item.data?.coord?.lat == i.lat && item.data?.coord?.lon == i.lon){
                                newLocationList.add(item)
                                isAvail = true
                            }
                        }
                        if(isAvail){
                            continue
                        }
                        newLocationList.add(WatchItemUI(WatchEnum.LOADING, null, LocationBasedResponse(coord = Coord(lat = i.lat, lon = i.lon))))
                    }

                    _lastWatchList.value = newLocationList
                }
            }
        }
    }


    fun observeWatchList(){
        viewModelScope.launch {
            _lastWatchList.collectLatest { item ->
                var tempList = item.toMutableList()
                for(index in tempList.indices) {
                    if(tempList[index].state == WatchEnum.LOADING){
                        weatherApiInterface.getCurrentWeatherBasedOnLocation(Location(tempList[index].data?.coord?.lat!!, tempList[index].data?.coord?.lon!!)).collect{ response ->
                            if(response is ResponseHandler.Success){
                                tempList[index] = tempList[index].copy(
                                    state = WatchEnum.SUCCESS,
                                    data = response.data
                                )
                            }
                            else if(response is ResponseHandler.Error){
                                tempList[index] = tempList[index].copy(
                                    state = WatchEnum.ERROR,
                                    message = response.message?:"Something went wrong"
                                )
                            }
                        }
                    }
                }
                _lastWatchList.value = tempList
            }
        }
    }

    fun updateWatchListItem(index : Int){

        viewModelScope.launch {
            var tempList  = _lastWatchList.value.toMutableList()
            tempList[index] =  tempList[index].copy(state = WatchEnum.LOADING, message = null)
            _lastWatchList.value = tempList

//            weatherApiInterface.getCurrentWeatherBasedOnLocation(Location(tempList[index].data?.coord?.lat!!, tempList[index].data?.coord?.lon!!)).collect{ response ->
//                if(response is ResponseHandler.Success){
//                    tempList[index] = tempList[index].copy(
//                        state = WatchEnum.SUCCESS,
//                        data = response.data
//                    )
//                    _lastWatchList.value = tempList
//                }
//                else if(response is ResponseHandler.Error){
//                    tempList[index] = tempList[index].copy(
//                        state = WatchEnum.ERROR,
//                        message = response.message?:"Something went wrong"
//                    )
//                    _lastWatchList.value = tempList
//                }
//            }

        }
    }

    fun getFutureWeather(location: Location?){
        if(futureWeatherOfLocation.value==null && location == null) {
            return
        }
        viewModelScope.launch {
            weatherApiInterface.getFutureWeather(location!!).collect{
                _futureWeatherOfLocation.value = it
            }
        }
    }


    fun getLocationBasedOnSearch(location: String){

        viewModelScope.launch(Dispatchers.IO){
            weatherApiInterface.getListOfLocation(location).collect{

                when(it){
                    is ResponseHandler.Success -> {

                        _getSearchHistory.value = it.data
                    }

                    else -> {
                    }
                }

            }
        }
    }

    fun deleteFromSavedList(savedListDTO: SavedListDTO){
        viewModelScope.launch {
            appDatabase.getSavedListDao().deleteFromSavedList(savedListDTO)
        }
    }
}