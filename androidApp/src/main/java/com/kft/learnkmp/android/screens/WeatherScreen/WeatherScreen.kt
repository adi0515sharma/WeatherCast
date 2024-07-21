package com.kft.learnkmp.android.screens.WeatherScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.android.components.FailedScreen
import com.kft.learnkmp.android.components.TodayWeatherConditions
import com.kft.learnkmp.android.screens.Utils.DetectWeather
import com.kft.learnkmp.model.Database.WatchList.WatchListDTO
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import com.kft.learnkmp.ui.WeatherConditionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    navController :  NavController,
    location : String,
    weatherConditionalViewModel : WeatherConditionViewModel = koinViewModel()
){

    val currentWeatherOfLocation by  weatherConditionalViewModel.currentWeatherOfLocation.collectAsState()
    val futureWeatherOfLocation by weatherConditionalViewModel.futureWeatherOfLocation.collectAsState()
    val isSaved by weatherConditionalViewModel.isSaved.collectAsState()
    var refreshPage by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = refreshPage){

        if(!refreshPage){
            return@LaunchedEffect
        }

        try{
           val location : Location = Gson().fromJson(location, Location::class.java)
           weatherConditionalViewModel.getCurrentWeather(location)
        }
        catch (e : Exception){
            weatherConditionalViewModel.getCurrentWeather(location)
        }
        refreshPage = false
    }

    LaunchedEffect(currentWeatherOfLocation){
        Log.e("LearnKMP", currentWeatherOfLocation.toString())
    }
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar =  {
            Column (modifier = Modifier.fillMaxWidth()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                        IconButton(onClick = {navController.popBackStack()}) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "back btn",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
                            )
                        }


                    if(currentWeatherOfLocation is ResponseHandler.Success){
                        Row (modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center){
                            Text(
                                text = "${(currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data.name}, ${(currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data.sys?.country}",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                    if(currentWeatherOfLocation is ResponseHandler.Success){
                        IconButton(onClick = {
                            if(isSaved!=null){
                                weatherConditionalViewModel.addWatchList(isSaved!!)
                            }
                            else{
                                weatherConditionalViewModel.addWatchList(WatchListDTO(lat = (currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data.coord?.lat, lon = (currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data.coord?.lon))
                            }


                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_watchlist_add_24),
                                contentDescription = "back btn",
                                colorFilter = if(isSaved != null) ColorFilter.tint(Color(0xFF3B91F4)) else ColorFilter.tint(Color.Gray)
                            )
                        }

                    }



                    }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.tertiary))
            }
        }
    ){
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            if(currentWeatherOfLocation is ResponseHandler.Error || futureWeatherOfLocation is ResponseHandler.Error) {
                FailedScreen {
                    refreshPage = true
                }
                return@Scaffold
            }

            if(currentWeatherOfLocation is ResponseHandler.Loading || futureWeatherOfLocation is ResponseHandler.Loading){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(50.dp)
                )
                return@Scaffold
            }



            Column(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()){
                WeatherFullScreen((currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data)
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.tertiary))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
                TodayWeatherConditions(futureWeatherOfLocation) {
                    if(currentWeatherOfLocation is ResponseHandler.Success){
                        val coords : Location = Location((currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data.coord?.lat!!, (currentWeatherOfLocation as ResponseHandler.Success<LocationBasedResponse>).data.coord?.lon!!)
                        val location = Gson().toJson(coords)
                        navController.navigate("futureForecast?location=${location}")
                    }
                }
            }
        }
    }
}



@Composable
fun WeatherFullScreen(weatherDetail : LocationBasedResponse){

    Column(
        modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

            Image(
                painter = painterResource(id = DetectWeather.getWeather(weatherDetail.weather?.get(0)?.icon)),
                contentDescription = "",
                modifier = Modifier.size(150.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(text = "${weatherDetail.main?.temp}", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onPrimary)
                Text(text = "o", style = MaterialTheme.typography.headlineLarge, fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Row {
                    Text(text = "${weatherDetail.main?.temp_min}", style = MaterialTheme.typography.labelLarge,color =MaterialTheme.colorScheme.onPrimary)
                    Text(text = "o ", style = MaterialTheme.typography.labelMedium,fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.width(100.dp))
                Row {
                    Text(text = "${weatherDetail.main?.temp_max}", style = MaterialTheme.typography.labelLarge,color = MaterialTheme.colorScheme.onPrimary)
                    Text(text = "o ", style = MaterialTheme.typography.labelMedium,fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "${weatherDetail.weather?.get(0)?.description}",  color = Color.Gray, style = MaterialTheme.typography.titleLarge,)




    }
}