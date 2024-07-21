package com.kft.learnkmp.android.screens.Home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.kft.learnkmp.android.components.BottomNavigationUI
import com.kft.learnkmp.android.components.totalIconList
import com.kft.learnkmp.ui.MainActivityViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreenContainer(navController : NavController,  myViewModel : MainActivityViewModel = koinViewModel()){


    val locationBasedWeatherData  by myViewModel.currentWeatherOfLocation.collectAsState()
    val forecastBasedWeatherData  by myViewModel.futureWeatherOfLocation.collectAsState()
    val lastSearchHistory by myViewModel.lastSearchHistoryFromDB.collectAsState()
    val getSearchResult by myViewModel.getSearchHistory.collectAsState()
    val lastWatchList by myViewModel.lastWatchList.collectAsState()

    val bottomNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationUI(bottomNavController)
        },

    ){ it ->
        NavHost(modifier = Modifier.padding(it), navController = bottomNavController, startDestination = totalIconList[0].route) {
            composable(route = totalIconList[0].route){
                HomePage(

                    navigateToForeCast = {
                        val location = Gson().toJson(myViewModel.currentLocation)
                        navController.navigate("futureForecast?location=${location}")
                    },
                    {
                        myViewModel.fetchHomePageData()
                    },
                    locationBasedWeatherData = locationBasedWeatherData,
                    forecastBasedWeatherData = forecastBasedWeatherData
                )
            } 
            composable(route = totalIconList[1].route){

                var currentSearch by remember {
                    mutableStateOf(TextFieldValue(""))
                }

                var searchScreenTitle by rememberSaveable {
                    mutableStateOf("")
                }

                LaunchedEffect(key1 = currentSearch.text){
                    if(currentSearch.text.isNullOrEmpty()){
                        searchScreenTitle = "Previous Search History"
                        return@LaunchedEffect
                    }

                    searchScreenTitle = "Search Result"
                    myViewModel.getLocationBasedOnSearch(currentSearch.text)
                }


                SearchPage(
                    searchScreenTitle,

                    currentSearch,
                    { it : TextFieldValue ->
                        currentSearch = it
                    },
                    getSearchResult,
                    lastSearchHistory,
                    { it : String, isLatLongAvailable->

                        if(isLatLongAvailable){
                            navController.navigate("weatherScreen?&coord=${it}")

                        }
                        else{
                            navController.navigate("weatherScreen?location=${it}")

                        }
                    },
                    {
                        it->
                        myViewModel.deleteFromSavedList(it)
                    }
                )
            }
            composable(route = totalIconList[2].route){



                LibraryPage(
                    lastWatchList,
                    { item ->
                        navController.navigate("weatherScreen?&coord=${item}")

                    },
                    { index ->
                        myViewModel.updateWatchListItem(index)

                    }
                )
            }
        }
    }
}