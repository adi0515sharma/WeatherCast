package com.kft.learnkmp.model.LocationBasedResponse
import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int ? = null,
    val speed: Double ? = null
)