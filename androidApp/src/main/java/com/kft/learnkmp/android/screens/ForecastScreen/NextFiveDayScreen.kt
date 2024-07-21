package com.kft.learnkmp.android.screens.ForecastScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.android.components.FailedScreen
import com.kft.learnkmp.android.screens.Utils.Converts
import com.kft.learnkmp.android.screens.Utils.DetectWeather
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import com.kft.learnkmp.ui.Next5DaysForecastViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun NextFiveDayScreen(
    navController: NavController,
    location: Location,
    futureForecastViewModel : Next5DaysForecastViewModel =  koinViewModel()
){

    var forecast by rememberSaveable {
        mutableStateOf(emptyList<LocationBasedResponse>())
    }

    var locationName by rememberSaveable {
        mutableStateOf<String?>("")
    }

    var loading by rememberSaveable {
        mutableStateOf(true)
    }

    var isError by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = null){

        futureForecastViewModel.futureWeatherOfLocation.collectLatest {
            when(it){
                is ResponseHandler.Success->{
                    locationName = "${it.data.city.name}, ${it.data.city.country}"
                    forecast = Converts.getFuturesForecast(it.data.list)
                    Log.e("LearnKMP", forecast.toString())
                    loading = false
                    isError = false
                }
                is ResponseHandler.Error->{
                    isError = true
                    loading = false
                }
                else -> {

                }
            }
        }
    }
    LaunchedEffect(key1 = loading){
        if(!loading){
            return@LaunchedEffect
        }
        futureForecastViewModel.getFutureWeather(location)
    }

    val groupedItems = forecast.groupBy { it -> it.dt_txt?.substring(0, 10) }

    Scaffold(
        topBar =  {

            Column (modifier = Modifier.fillMaxWidth()){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {navController.popBackStack()}) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "back btn",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
                            )
                        }
                    }

                    Text(
                        text = "$locationName",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.tertiary))
            }
           
            
        }
    ){
        if(loading){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(50.dp)
                )
            }
            return@Scaffold
        }

        if(isError){
            FailedScreen {
                loading = true
            }
            return@Scaffold
        }

        Column(modifier = Modifier.padding(it)) {

            LazyColumn(modifier = Modifier.fillMaxSize()){

                groupedItems.forEach { (date, itemsForDate) ->
                    item {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp), horizontalArrangement = Arrangement.Center){
                            Text(
                                text = date!!,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                    }
                    items(itemsForDate) { item ->
                        ForecastItem(item)
                    }
                }

            }
        }

    }

}

@Composable
fun ForecastItem(locationBasedResponse: LocationBasedResponse){

    Row (
        modifier = Modifier
            .padding(vertical = 3.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(text = Converts.convertUnixTimestampToTime(locationBasedResponse.dt!!), style = MaterialTheme.typography.headlineLarge, fontSize = 25.sp)
        Text(text = "${locationBasedResponse.weather?.get(0)?.description}", style = MaterialTheme.typography.titleMedium,color = Color(0xFFE3E9EF))
        Image(
            painter = painterResource(id = DetectWeather.getWeather(locationBasedResponse.weather?.get(0)?.icon)),
            contentDescription = "weather icon",
            modifier = Modifier.size(60.dp),
        )
    }
}