package com.kft.learnkmp.model.LocationBasedResponse
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String ? = null,
    val id: Int ? = null,
    val message: Double ? = null,
    val sunrise: Int ? = null,
    val sunset: Int ? = null,
    val type: Int ? = null
)