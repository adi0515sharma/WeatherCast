package com.kft.learnkmp.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kft.learnkmp.android.R

@Composable
fun BottomNavigationUI(bottomNavHostController: NavHostController){
    var currentSelectedIndex by rememberSaveable {
        mutableStateOf(totalIconList[0].route)
    }

    val navBackStackEntry by bottomNavHostController.currentBackStackEntryAsState()

    // Observe the current destination and invoke the callback with the route
    navBackStackEntry?.destination?.route?.let { route ->
        currentSelectedIndex = route
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
        totalIconList.forEach {
            BottomNavigationItem(
                Modifier.clickable {
                    bottomNavHostController.navigate(it.route)
                    currentSelectedIndex = it.route
                },
                "${it.route}",
                it.icon,
                currentSelectedIndex == it.route
            )
        }
    }
}

data class IconData(
    val route : String,
    val icon : Int
)

val totalIconList = listOf<IconData>(
    IconData("Home",  R.drawable.baseline_home_24),
    IconData("Search",  R.drawable.baseline_search_24),
    IconData("WatchList",  R.drawable.baseline_watchlist_add_24)
)
@Composable
fun BottomNavigationItem(
    modifier: Modifier,
    title : String,
    icon : Int,
    isSelected : Boolean
){

    Column(modifier = modifier.padding(vertical =6.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier = Modifier
            .background(color = if(isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 4.dp, horizontal = 12.dp)

        ){

            Image(
                painter = painterResource(id = icon),
                contentDescription = "icon",
                modifier = Modifier
                    .size(30.dp),
                colorFilter = ColorFilter.tint(if(isSelected) Color.LightGray else Color.Gray)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium)
    }
}