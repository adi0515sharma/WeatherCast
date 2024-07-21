package com.kft.learnkmp.Utils

data class Location(val latitude: Double, val longitude: Double)


interface LocationAccess {
    suspend fun getCurrentLocation() : Location?
}

expect fun getLocationAccess(): LocationAccess