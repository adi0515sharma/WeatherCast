package com.kft.learnkmp.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse


@Composable
fun GetGreeting(){
    Column {
        Text(
            text = "Good Morning", 
            style = MaterialTheme.typography.headlineMedium, 
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "How was your day ?",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun GetLocation(locationBasedWeatherData: ResponseHandler<LocationBasedResponse>?) {

    if(locationBasedWeatherData == null || locationBasedWeatherData !is ResponseHandler.Success){
        return
    }
    Row (
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF009FFF),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(vertical = 5.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Image(
            painter = painterResource(id = R.drawable.baseline_add_location_24),
            contentDescription = "Location Icon"
        )
        Text(
            text = "${locationBasedWeatherData.data.name} , ${locationBasedWeatherData.data.sys?.country}",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF009FFF),
        )
    }
}

@Composable
fun HomeScreenHeader(locationBasedWeatherData: ResponseHandler<LocationBasedResponse>?) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        GetGreeting()
        GetLocation(locationBasedWeatherData)
    }
}