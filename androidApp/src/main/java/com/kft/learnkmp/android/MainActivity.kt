package com.kft.learnkmp.android

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.android.screens.ForecastScreen.NextFiveDayScreen
import com.kft.learnkmp.android.screens.Home.HomeScreenContainer
import com.kft.learnkmp.android.screens.WeatherScreen.WeatherScreen


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {


            AppTheme {


                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {

                    var isLocationPermissionProvided by rememberSaveable { mutableStateOf(false) }
                    var isLocationSettingsEnabled by rememberSaveable { mutableStateOf(false) }


                    PermissionHandler("Location",
                        ACCESS_FINE_LOCATION,
                        true
                    ){
                        isLocationPermissionProvided = true
                    }

                    if(!isLocationPermissionProvided){
                        return@Surface
                    }

                    DeviceLocation({
                        isLocationSettingsEnabled = true
                    }, true)

                    if(!isLocationSettingsEnabled){
                        return@Surface
                    }

                    App()
                }
            }
        }


    }


    @Composable
    fun App(){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreenContainer(navController) }
            composable("weatherScreen?location={location}&coord={coord}", arguments = listOf(
                navArgument(
                    name = "location"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "coord"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )) {
                val locationStr = it.arguments?.getString("location") ?: ""
                val locationCoords = it.arguments?.getString("coord") ?: ""
                if(locationStr.isNullOrEmpty() && locationCoords.isNullOrEmpty()){
                    return@composable
                }

                val location : String = if(!locationStr.isNullOrEmpty()){
                    locationStr
                }
                else{
                    locationCoords
                }

                WeatherScreen(navController, location)
            }
            composable("futureForecast?location={location}", arguments = listOf(
                navArgument(
                    name = "location"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) { it->
                val locationStr = it.arguments?.getString("location") ?: return@composable
                val location = Gson().fromJson(locationStr, Location::class.java)

                NextFiveDayScreen(navController, location)

            }
        }
    }
}


@Composable
fun DeviceLocation(onPermissionGranted: () -> Unit,shouldShowDialogAfterDenied : Boolean = false,){
    val context = LocalContext.current
    var checkPermissionState by rememberSaveable { mutableStateOf(true) }
    var  popUpState by rememberSaveable { mutableStateOf<String?>(null) }

    val resolutionForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        // Not Granted// Granted
        if(result.resultCode == Activity.RESULT_OK){
            onPermissionGranted()
        }
        else{
            if(shouldShowDialogAfterDenied){
                popUpState = "shouldShowDialogAfterDenied"
            }
        }
    }

    fun checkLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10 * 1000L
            fastestInterval = 2 * 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        LocationServices.getSettingsClient(context)
            .checkLocationSettings(builder.build())
            .addOnSuccessListener { response ->
                // Location settings are satisfied, start location updates
                onPermissionGranted()
                // startUpdatingLocation()
            }
            .addOnFailureListener { ex ->
                if (ex is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest.Builder(ex.resolution).build()
                        resolutionForResult.launch(intentSenderRequest)
                    } catch (exception: Exception) {
                        Log.d("LocationSettings", "enableLocationSettings: $exception")
                    }
                }
            }
    }

    LaunchedEffect(checkPermissionState){
        if(checkPermissionState){
            checkLocationSettings()
            checkPermissionState = false
        }
    }

    if(popUpState != null){
        AlertDialogPopup(
            "Device Location Permission",
            "PLease provide the Device Location permission so that we can fetch weather data based on your current location.",
            {
                checkPermissionState = true
                popUpState = null
            },
            {
                popUpState = null
            }
        )
    }

}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    permissionName: String,
    permission: String,
    shouldShowDialogAfterDenied : Boolean = false,
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission)

    var permissionGranted by rememberSaveable { mutableStateOf(false) }
    var checkPermissionState by rememberSaveable { mutableStateOf(true) }
    var  popUpState by rememberSaveable { mutableStateOf<String?>(null) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted

        if (!isGranted && shouldShowDialogAfterDenied){
            popUpState = "shouldShowDialogAfterDenied"
        }
    }




    fun checkPermission() {
        permissionGranted = ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted){
            if (cameraPermissionState.status.shouldShowRationale) {
                popUpState = "rational"
            } else {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    LaunchedEffect(checkPermissionState) {
        if(checkPermissionState){
            checkPermission()
            checkPermissionState = false
        }
    }

    LaunchedEffect(cameraPermissionState.status){
        if(cameraPermissionState.status == PermissionStatus.Granted){
            onPermissionGranted()
        }
    }

    if(popUpState == null){
        return
    }

    if(popUpState == "rational"){
        AlertDialogPopup(
            "${permissionName} Permission",
            "Look like you don't allowed the permission for forever please go to settings and enable the ${permissionName} permission.",
            {
                openAppSettings(context)
                popUpState = null
            },
            {
                popUpState = null
            }
        )
    }
    else{
        AlertDialogPopup(
            "${permissionName} Permission",
            "PLease provide the ${permissionName} permission so that we can fetch weather data based on your current location.",
            {
                checkPermissionState = true
                popUpState = null
            },
            {
                popUpState = null
            }
        )
    }

}

fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}
@Composable
fun AlertDialogPopup(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("CANCEL")
            }
        }
    )
}
//

//val resolutionForResult = rememberLauncherForActivityResult(
//    ActivityResultContracts.StartIntentSenderForResult()
//) { result ->
//    // Not Granted// Granted
//    isLocationSettingsEnabled = result.resultCode == Activity.RESULT_OK
//}
//
//fun checkIfLocationEnabled() : Boolean{
//    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//}
//
//
//fun checkLocationSettings() {
//    val locationRequest = LocationRequest.create().apply {
//        interval = 10 * 1000L
//        fastestInterval = 2 * 1000L
//        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//    }
//
//    val builder = LocationSettingsRequest.Builder()
//        .addLocationRequest(locationRequest)
//
//    LocationServices.getSettingsClient(this)
//        .checkLocationSettings(builder.build())
//        .addOnSuccessListener { response ->
//            // Location settings are satisfied, start location updates
//            isLocationSettingsEnabled = true
//            // startUpdatingLocation()
//        }
//        .addOnFailureListener { ex ->
//            if (ex is ResolvableApiException) {
//                try {
//                    val intentSenderRequest = IntentSenderRequest.Builder(ex.resolution).build()
//                    resolutionForResult.launch(intentSenderRequest)
//                } catch (exception: Exception) {
//                    Log.d("LocationSettings", "enableLocationSettings: $exception")
//                }
//            }
//        }
//}
