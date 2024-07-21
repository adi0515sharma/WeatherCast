package com.kft.learnkmp.android.screens.Utils

import android.icu.util.TimeZone
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.kft.learnkmp.model.LocationBasedResponse.LocationBasedResponse
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.TimeSource

object Converts {

    fun convertUnixTimestampToTime(unixTimestamp: Long): String {
        // Convert Unix timestamp to Instant
        // Convert Unix timestamp to Instant
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val instant = Instant.ofEpochSecond(unixTimestamp)

            // Create a DateTimeFormatter for the desired time format
            val formatter = DateTimeFormatter.ofPattern("h a", Locale.ENGLISH)
                .withZone(ZoneId.systemDefault())

            // Format the Instant to the desired time format
            return formatter.format(instant)
        }
        else{
            return unixTimestamp.toString()
        }
    }

    fun getTodaysForecast(forecasts: List<LocationBasedResponse>): List<LocationBasedResponse> {
        // Get the current date in the system's default time zone


        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val datetime: String = LocalDateTime.now().toString().split("T")[0]
            return forecasts.filter {
                it.dt_txt?.startsWith(datetime)!!
            }
        }
        else{
            return forecasts
        }
    }

    fun getFuturesForecast(forecasts: List<LocationBasedResponse>): List<LocationBasedResponse> {
        // Get the current date in the system's default time zone


        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val datetime: String = LocalDateTime.now().toString().split("T")[0]
            return forecasts.filter {
                !it.dt_txt?.startsWith(datetime)!!
            }
        }
        else{
            return forecasts
        }
    }

    fun Double.toFormattedSpeed(): String {
        val multipliedValue = this * 3.6
        return if (multipliedValue % 1 == 0.0) {
            multipliedValue.toInt().toString()
        } else {
            String.format("%.2f", multipliedValue)
        }
    }

    fun Int.toKilometers(): String {
        val kilometers = this * 0.001
        return if (kilometers % 1 == 0.0) {
            kilometers.toInt().toString()
        } else {
            String.format("%.2f", kilometers)
        }
    }

}