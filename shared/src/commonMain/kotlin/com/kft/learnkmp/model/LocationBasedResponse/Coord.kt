package com.kft.learnkmp.model.LocationBasedResponse
import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double ? = null,
    val lon: Double ? = null
)