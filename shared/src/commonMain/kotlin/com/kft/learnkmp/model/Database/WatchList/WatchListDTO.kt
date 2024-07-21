package com.kft.learnkmp.model.Database.WatchList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("WatchList")
data class WatchListDTO(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val lat : Double? = null,
    val lon : Double? = null
)
