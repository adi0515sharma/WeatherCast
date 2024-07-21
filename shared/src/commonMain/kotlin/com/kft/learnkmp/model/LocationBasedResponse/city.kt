package com.kft.learnkmp.model.LocationBasedResponse

import kotlinx.serialization.Serializable

@Serializable

data class city(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)