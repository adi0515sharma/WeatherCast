package com.kft.learnkmp.android.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.android.screens.Utils.Converts.convertUnixTimestampToTime
import com.kft.learnkmp.android.screens.Utils.Converts.getTodaysForecast
import com.kft.learnkmp.android.screens.Utils.DetectWeather
import com.kft.learnkmp.android.screens.Utils.DetectWeather.getWeather
import com.kft.learnkmp.model.LocationBasedResponse.ForecastBasedResponse
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import java.time.Clock


@Composable
fun TodayWeatherConditions(forecastBasedResponse : ResponseHandler<ForecastBasedResponse>?, navigateToForecast : () -> Unit) {

    if(forecastBasedResponse == null || forecastBasedResponse !is ResponseHandler.Success){
        return
    }
    Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Today" ,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Row {
                        Text(
                            text = "Next 5 Day's ",
                            color = Color(0xFF358EF4),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.clickable {

                                Log.e("LearnKMP", "cliked")
                                navigateToForecast()
                            }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = ""
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TimelyWeather(getTodaysForecast(forecastBasedResponse.data.list))
            }



}


@Composable
fun TimelyWeather(list: List<LocationBasedResponse>){


    LazyRow(modifier = Modifier.fillMaxWidth()){
        items((list.indices).toList()) { index ->
            Column(
                modifier = Modifier
                    .border(width = 1.dp, shape = RoundedCornerShape(50.dp), color = Color.Gray)
                    .padding(horizontal = 25.dp, vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${convertUnixTimestampToTime(list[index].dt!!)}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = getWeather(list[index].weather?.get(0)?.icon)),
                    contentDescription = "",
                    modifier = Modifier.size(60.dp),
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Row{
                        Text(text = "${list[index].main?.temp_min}",  color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall)
                        Text(text = "o",  color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall, fontSize = 10.sp)
                    }
                    Text(text = "/",  color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall)
                    Row{
                        Text(text = "${list[index].main?.temp_max}",  color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall)
                        Text(text = "o",  color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall, fontSize = 10.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "${list[index].weather?.get(0)?.description}",  color = Color.Gray, style = MaterialTheme.typography.labelMedium,)

            }


            Spacer(modifier = Modifier.width(10.dp))

        }
    }

}


