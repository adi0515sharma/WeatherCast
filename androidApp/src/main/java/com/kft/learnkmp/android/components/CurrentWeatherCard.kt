package com.kft.learnkmp.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kft.learnkmp.Utils.ResponseHandler
import com.kft.learnkmp.android.R
import com.kft.learnkmp.android.screens.Utils.DetectWeather.getWeather
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse



@Composable
fun CurrentWeatherCard(data: ResponseHandler<LocationBasedResponse>?) {

    if(data == null || data !is ResponseHandler.Success){
        return
    }


            Card(modifier = Modifier
                .fillMaxWidth(),
                colors = CardColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.secondary, disabledContainerColor = MaterialTheme.colorScheme.secondary, disabledContentColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(50.dp)
            ) {


                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){

                    Column {

                        Row {
                            Text(text = "${data.data.main?.temp}", style = MaterialTheme.typography.headlineLarge)
                            Text(text = "o", style = MaterialTheme.typography.headlineLarge, fontSize = 20.sp)
                        }

                        Row{
                            Text(text = "${data.data.main?.temp_max}", style = MaterialTheme.typography.labelSmall,color = Color(0xFFE3E9EF))
                            Text(text = "o ", style = MaterialTheme.typography.labelSmall,fontSize = 9.sp, color = Color(0xFFE3E9EF))
                            Text(text = "Max Temp / ${data.data.weather?.get(0)?.description}", style = MaterialTheme.typography.labelSmall,color = Color(0xFFE3E9EF))
                        }

                    }

                    Image(
                        painter = painterResource(id = getWeather(data.data.weather?.get(0)?.icon)),
                        contentDescription = "",
                        modifier = Modifier.size(80.dp),
                    )
                }
            }





}