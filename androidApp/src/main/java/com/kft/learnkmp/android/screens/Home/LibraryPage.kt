package com.kft.learnkmp.android.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.android.components.CurrentWeatherCard
import com.kft.learnkmp.android.screens.Utils.Converts.toFormattedSpeed
import com.kft.learnkmp.android.screens.Utils.DetectWeather
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.Main
import com.kft.learnkmp.model.LocationBasedResponse.Sys
import com.kft.learnkmp.model.LocationBasedResponse.Weather
import com.kft.learnkmp.model.WatchEnum
import com.kft.learnkmp.model.WatchItemUI

@Composable
fun LibraryPage(
    watchListDTO: List<WatchItemUI>,
    navigateTo: (String) -> Unit,
    retryItem: (Int) -> Unit
){
    Column (modifier = Modifier.fillMaxSize()){
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Watch List",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = MaterialTheme.colorScheme.tertiary)
        )
        LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(10.dp)){

            items(watchListDTO.indices.toList().reversed()){


                WatchWeatherCard(
                    Modifier
                        .clickable {
                            val location = Gson().toJson(
                                Location(
                                    watchListDTO[it].data?.coord?.lat!!,
                                    watchListDTO[it].data?.coord?.lon!!
                                )
                            )
                            navigateTo(location)
                        }
                        .padding(bottom = 10.dp),
                    watchListDTO[it]
                ){
                    retryItem(it)
                }


            }
        }
    }

}


@Composable
fun WatchWeatherCard(modifier: Modifier, data: WatchItemUI?, retryButton : () -> Unit) {

    if(data == null){
        return
    }




    Card(
        modifier = modifier
        .fillMaxWidth(),
        colors = CardColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.secondary, disabledContainerColor = MaterialTheme.colorScheme.secondary, disabledContentColor = MaterialTheme.colorScheme.secondary),
    ) {


        if(data.state == WatchEnum.LOADING){

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondary,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(50.dp)
                )
            }
            return@Card
        }
        if(data.state == WatchEnum.ERROR){

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        retryButton()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ){
                Image(
                    painter = painterResource(id = R.drawable.baseline_cached_24),
                    contentDescription = "reload icon",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondary)
                    )
            }
            return@Card
        }

            Column (
                modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Row {
                        Text(
                            text = "${data.data?.main?.temp}",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontSize = 40.sp
                        )
                        Text(
                            text = "o",
                            style = MaterialTheme.typography.titleLarge, fontSize = 20.sp,color = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.End){

                        Text(
                           text = "${data.data?.name}",
                           style = MaterialTheme.typography.headlineLarge,
                           fontSize = 18.sp
                        )
                        Text(
                            text = "${data.data?.sys?.country}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = 18.sp
                        )
                    }

                }

                Spacer(modifier = Modifier
                    .height(0.5.dp)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .background(color = Color.White))

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceAround){
                    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                        Image(painter = painterResource(id = R.drawable.wind_white), contentDescription = "wind icon",modifier = Modifier.size(22.dp))
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(

                            text = "${data.data?.wind?.speed?.toFormattedSpeed()} km/h",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontSize = 17.sp
                        )
                    }
                    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                        Image(painter = painterResource(id = R.drawable.humidity_white), contentDescription = "humidity icon",modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text ="${data.data?.main?.humidity}%",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontSize = 17.sp
                        )
                    }
                    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                        Image(painter = painterResource(id = R.drawable.resilience_white), contentDescription = "pressure icon",modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(

                            text ="${data.data?.main?.humidity}mb",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,

                            fontSize = 17.sp
                        )
                    }
                }

            }
    }
}


//@Composable
//fun PreviewWeatherCard(){
//    WatchWeatherCard(Modifier, WatchItemUI(
//        state = WatchEnum.SUCCESS,
//        message = null,
//        data = LocationBasedResponse(name = "Lucknow", sys = Sys(country = "IN"), main = Main(temp = 12.9, temp_max = 13.5, temp_min = 10.3), weather = listOf(
//            Weather(description = "coludy rain")
//        ))
//    )
//    )
//}
//
