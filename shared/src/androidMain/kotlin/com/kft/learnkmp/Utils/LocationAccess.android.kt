package com.kft.learnkmp.Utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.asDeferred
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class AndroidLocationPlatform : LocationAccess {


    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation() : Location? = suspendCancellableCoroutine{ continuation ->

        Log.e("LearnKMP", "Fetching ..... ")


        LocationServices.getFusedLocationProviderClient(applicationContext).getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: android.location.Location? ->
                if (location == null)
                    Toast.makeText(applicationContext, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    val lat = location.latitude
                    val lon = location.longitude

                    continuation.resume(Location(location.latitude, location.longitude))

                }

            }
            .addOnFailureListener { exception->
                Log.e("LearnKMP", "Got exception ")

                exception.printStackTrace()
                continuation.resumeWithException(exception)

            }


//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location : android.location.Location? ->
//                Log.e("LearnKMP", "Got Location... ")
//
//                if(location!=null){
//                    Log.e("LearnKMP", "Got Location is not null ")
//
//                    continuation.resume(Location(location.latitude, location.longitude))
//                }
//            }
//            .addOnFailureListener { exception->
//                Log.e("LearnKMP", "Got exception ")
//
//                exception.printStackTrace()
//                continuation.resumeWithException(exception)
//
//            }

        continuation.invokeOnCancellation {
            // Clean up resources if necessary
        }

    }


}

actual fun getLocationAccess(): LocationAccess = AndroidLocationPlatform()