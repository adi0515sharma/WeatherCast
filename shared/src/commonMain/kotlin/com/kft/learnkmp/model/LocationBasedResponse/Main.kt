package com.kft.learnkmp.model.LocationBasedResponse
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val humidity: Int ? = null,
    val pressure: Int ? = null,
    val temp: Double ? = null,
    val temp_max: Double ? = null,
    val temp_min: Double ? = null
)