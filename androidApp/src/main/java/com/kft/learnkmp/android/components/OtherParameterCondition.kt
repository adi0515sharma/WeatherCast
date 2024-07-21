package com.kft.learnkmp.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.android.screens.Utils.Converts.toFormattedSpeed
import com.kft.learnkmp.android.screens.Utils.Converts.toKilometers
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse


@Composable
fun OtherParameterCondition(locationBasedWeatherData: ResponseHandler<LocationBasedResponse>?) {
    if(locationBasedWeatherData == null || locationBasedWeatherData !is ResponseHandler.Success){
        return
    }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ){

                Row(modifier = Modifier.fillMaxWidth()){

                    WindParam(Modifier.weight(1f),locationBasedWeatherData.data.wind?.speed)
                    Spacer(modifier = Modifier.width(5.dp))
                    HumidityParam(Modifier.weight(1f),locationBasedWeatherData.data.main?.humidity)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth()){

                    PressureParam(Modifier.weight(1f),locationBasedWeatherData.data.main?.pressure)
                    Spacer(modifier = Modifier.width(5.dp))
                    VisibilityParam(Modifier.weight(1f),locationBasedWeatherData.data.visibility)
                }
            }



}


@Composable
fun UVParam(){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)){
        Image(painter = painterResource(id = R.drawable.sun), contentDescription = "uv icon", modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Very High", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "UV index", style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun WindParam(modifier: Modifier,speed: Double?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ){
        Spacer(modifier = Modifier.height(2.dp))
        Image(painter = painterResource(id = R.drawable.wind), contentDescription = "wind icon",modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = "${speed?.toFormattedSpeed()} km/h", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Wind", style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun HumidityParam(modifier: Modifier, humidity: Int?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ){
        Spacer(modifier = Modifier.height(2.dp))
        Image(painter = painterResource(id = R.drawable.humidity), contentDescription = "humidity icon",modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if(humidity!=null) "${humidity}%" else "Not available", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Humidity", style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}


@Composable
fun DewPointParam(){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
        Image(painter = painterResource(id = R.drawable.dew), contentDescription = "dew icon",modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(5.dp))

        Row {

            Text(
                text = "24", style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Text(
                text = "o", style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Dew Point", style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}


@Composable
fun PressureParam(modifier: Modifier, pressure: Int?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ){
        Spacer(modifier = Modifier.height(2.dp))
        Image(painter = painterResource(id = R.drawable.resilience), contentDescription = "Pressure icon",modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if(pressure!=null) "${pressure}mb" else "Not available", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))


        Text(
            text = "Pressure", style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun VisibilityParam(modifier: Modifier, visibility: Int?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ){
        Spacer(modifier = Modifier.height(2.dp))
        Image(painter = painterResource(id = R.drawable.visibility), contentDescription = "Visibility icon",modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${visibility?.toKilometers()} km", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))


        Text(
            text = "Visibility", style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}
