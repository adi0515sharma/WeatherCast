package com.kft.learnkmp.android.screens.Home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.components.CurrentWeatherCard
import com.kft.learnkmp.android.components.FailedScreen
import com.kft.learnkmp.android.components.HomeScreenHeader
import com.kft.learnkmp.android.components.OtherParameterCondition
import com.kft.learnkmp.android.components.TodayWeatherConditions
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse

@Composable
fun HomePage(
    navigateToForeCast : () -> Unit,
    retryHandler : () -> Unit,
    locationBasedWeatherData : ResponseHandler<LocationBasedResponse>?,
    forecastBasedWeatherData : ResponseHandler<ForecastBasedResponse>?
){

    LaunchedEffect(locationBasedWeatherData){
        Log.e("LearnKMP",locationBasedWeatherData.toString())
    }


    if(locationBasedWeatherData is ResponseHandler.Loading || forecastBasedWeatherData is ResponseHandler.Loading){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                strokeWidth = 3.dp,
                modifier = Modifier.size(50.dp)
            )
        }
        return
    }


    if(locationBasedWeatherData is ResponseHandler.Error || forecastBasedWeatherData is ResponseHandler.Error){
        FailedScreen(){
            retryHandler()
        }
        return
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp))
    {


        HomeScreenHeader(locationBasedWeatherData)
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            item {

                CurrentWeatherCard(locationBasedWeatherData)
                Spacer(modifier = Modifier.height(20.dp))

                TodayWeatherConditions(forecastBasedWeatherData) {

                    navigateToForeCast()

                }
                Spacer(modifier = Modifier.height(20.dp))
                OtherParameterCondition(locationBasedWeatherData)
            }



        }
    }


}