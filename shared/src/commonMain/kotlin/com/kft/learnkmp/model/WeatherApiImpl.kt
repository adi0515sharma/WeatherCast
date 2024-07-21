package com.kft.learnkmp.model

import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.domain.WeatherApiInterface
import com.kft.learnkmp.model.Database.SaveList.SavedListDTO
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherApiImpl : WeatherApiInterface {

    val ktorClient = APIConstants.client



    override fun getCurrentWeatherBasedOnLocation(location: Location) = flow {

        emit(ResponseHandler.Loading)
        try{
            val response : HttpResponse = ktorClient.KtorClient.get {
                url ("${APIConstants.BASE_URL}/data/2.5/weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&appid=${APIConstants.appId}")
            }

            if (response.status == HttpStatusCode.OK) {
                val locationBasedResponse: LocationBasedResponse =
                    response.body<LocationBasedResponse>()
                emit(ResponseHandler.Success(locationBasedResponse))
            } else {
                emit(ResponseHandler.Error("Error: ${response.status}", null))
            }


        }
        catch (e : Exception){
            emit(ResponseHandler.Error(e.message, e.cause))
        }




    }

    override fun getFutureWeather(location: Location) = flow {
        emit(ResponseHandler.Loading)
        try{
            val response : HttpResponse = ktorClient.KtorClient.get {
                url ("${APIConstants.BASE_URL}/data/2.5/forecast?lat=${location.latitude}&lon=${location.longitude}&units=metric&appid=${APIConstants.appId}")
            }

            if (response.status == HttpStatusCode.OK) {
                val forecastBasedResponse: ForecastBasedResponse =
                    response.body<ForecastBasedResponse>()
                emit(ResponseHandler.Success(forecastBasedResponse))
            } else {
                emit(ResponseHandler.Error("Error: ${response.status}", null))
            }


        }
        catch (e : Exception){

            e.printStackTrace()
            emit(ResponseHandler.Error(e.message, e.cause))
        }
    }

    override fun getListOfLocation(location: String) = flow {
        emit(ResponseHandler.Loading)
        try{
            val response : HttpResponse = ktorClient.KtorClient.get {
                url ("${APIConstants.BASE_URL}/geo/1.0/direct?q=${location}&limit=${APIConstants.limitOfSearchLocation}&appid=${APIConstants.appId}")
            }

            if (response.status == HttpStatusCode.OK) {
                val forecastBasedResponse: List<SavedListDTO> = response.body<List<SavedListDTO>>()
                emit(ResponseHandler.Success(forecastBasedResponse))
            } else {

                emit(ResponseHandler.Error("Error: ${response.status}", null))
            }


        }
        catch (e : Exception){

            e.printStackTrace()
            emit(ResponseHandler.Error(e.message, e.cause))
        }
    }

    override fun getWeatherOfCity(location: String) = flow {


        emit(ResponseHandler.Loading)
        try{


            val response : HttpResponse = ktorClient.KtorClient.get {
                url ("${APIConstants.BASE_URL}/data/2.5/weather?q=${location}&units=metric&appid=${APIConstants.appId}")
            }



            if (response.status == HttpStatusCode.OK) {
                val locationBasedResponse: LocationBasedResponse =
                    response.body<LocationBasedResponse>()
                emit(ResponseHandler.Success(locationBasedResponse))
            } else {
                emit(ResponseHandler.Error("Error: ${response.status}", null))
            }


        }
        catch (e : Exception){
            emit(ResponseHandler.Error(e.message, e.cause))
        }

    }
}