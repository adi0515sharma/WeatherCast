package com.kft.learnkmp.model.LocationBasedResponse

import kotlinx.serialization.Serializable

@Serializable
data class LocationBasedResponse(
    val base: String? = null,
    val clouds: Clouds ? = null,
    val cod: Int ? = null,
    val coord: Coord ? = null,
    val dt: Long ? = null,
    val id: Int ? = null,
    val main: Main ? = null,
    val name: String ? = null,
    val sys: Sys ? = null,
    val visibility: Int ? = null,
    val weather: List<Weather> ? = null,
    val wind: Wind ? = null,
    val dt_txt: String? = null
)