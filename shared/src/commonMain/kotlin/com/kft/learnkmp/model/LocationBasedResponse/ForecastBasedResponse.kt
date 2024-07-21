package com.kft.learnkmp.model.LocationBasedResponse

import kotlinx.serialization.Serializable

@Serializable
data class ForecastBasedResponse(
    val cod : String,
    val message : Int,
    val cnt : Int,
    val list : List<LocationBasedResponse>,
    val city : city
)